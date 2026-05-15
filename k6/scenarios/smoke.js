import http from 'k6/http';
import {check, sleep} from 'k6';
import {BASE_URL, DEFAULT_HEADERS, SMOKE_OPTIONS} from '../config.js';
import { htmlReport } from '../lib/k6-reporter.js';

export const options = SMOKE_OPTIONS;

const KEYWORDS = ['클린코드', '자바', '스프링', '알고리즘', '파이썬',
    '리팩토링', '객체지향', '데이터베이스', '네트워크', 'MSA'];

export default function () {
    const keyword = KEYWORDS[Math.floor(Math.random() * KEYWORDS.length)];

    const searchRes = http.get(
        `${BASE_URL}/api/v1/books/search?query=${encodeURIComponent(keyword)}&queryType=Title&page=0&size=10`,
        {
            headers: DEFAULT_HEADERS,
            tags: {name: '검색_smoke'},
        }
    );

    const searchOk = check(searchRes, {
        '검색 status 200': (r) => r.status === 200,
        '검색 응답 content 존재': (r) => {
            try {
                const body = r.json();
                return body.data && body.data.content && body.data.content.length > 0;
            } catch {
                return false;
            }
        },
    });

    if (!searchOk) {
        console.warn(`검색 실패 keyword: ${keyword} status: ${searchRes.status}`);
        sleep(2);
        return;
    }

    const books = searchRes.json('data.content');
    const isbn = books[Math.floor(Math.random() * books.length)].bookId;

    if (!isbn) {
        console.warn('isbn 추출 실패');
        sleep(1);
        return;
    }

    const detailRes = http.get(
        `${BASE_URL}/api/v1/books/${isbn}`,
        {
            headers: DEFAULT_HEADERS,
            tags: {name: '상세조회_smoke'},
        }
    );

    check(detailRes, {
        '상세조회 status 200': (r) => r.status === 200,
        '상세조회 isbn 존재': (r) => {
            try {
                return r.json('data.isbn') !== null;
            } catch {
                return false;
            }
        },
        '상세조회 likeCount 존재': (r) => {
            try {
                return r.json('data.likeCount') !== undefined;
            } catch {
                return false;
            }
        },
        '상세조회 reportCount 존재': (r) => {
            try {
                return r.json('data.reportCount') !== undefined;
            } catch {
                return false;
            }
        },
    });

    console.log(`적재 완료 isbn: ${isbn}`);

    sleep(2);
}

export function handleSummary(data) {
    const totalReqs = data.metrics.http_reqs.values.count;
    const failedRate = (data.metrics.http_req_failed.values.rate * 100).toFixed(2);
    const avgDuration = data.metrics.http_req_duration.values.avg.toFixed(0);

    console.log('');
    console.log('========== Smoke Test 완료 ==========');
    console.log(`총 요청 수    : ${totalReqs}`);
    console.log(`에러율        : ${failedRate}%`);
    console.log(`평균 응답시간 : ${avgDuration}ms`);
    console.log('');
    console.log('[다음 단계]');
    console.log('1. 위 로그에서 적재된 isbn 확인');
    console.log('2. DB에서 SELECT COUNT(*) FROM p_book; 로 적재 건수 확인');
    console.log('3. isbn_list.json 준비 후 load.js 실행');
    console.log('=====================================');

    return {
        'k6/results/smoke_summary.json': JSON.stringify(data, null, 2),
        'k6/results/smoke_summary.html': htmlReport(data),
    };
}
