import http, {setResponseCallback} from 'k6/http';
import {check, sleep} from 'k6';
import {SharedArray} from 'k6/data';
import {BASE_URL, DEFAULT_HEADERS, LOAD_OPTIONS} from '../config.js';


setResponseCallback(http.expectedStatuses(200, 201, 400, 404));
export const options = LOAD_OPTIONS;

const isbns = new SharedArray('isbns', function () {
    return JSON.parse(open('../data/isbn_list.json'));
});

export default function () {
    const roll = Math.random();

    if (roll < 0.80) {
        getBookDetail();
    } else if (roll < 0.95) {
        likeBook();
    } else {
        unlikeBook();
    }

    sleep(Math.random() * 2 + 0.5);
}

function getBookDetail() {
    const isbn = isbns[Math.floor(Math.random() * isbns.length)];

    const res = http.get(
        `${BASE_URL}/api/v1/books/${isbn}`,
        {
            headers: DEFAULT_HEADERS,
            tags: {name: '상세조회'},
        }
    );

    check(res, {
        '상세조회 status 200': (r) => r.status === 200,
        '상세조회 응답시간 500ms': (r) => r.timings.duration < 500,
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
}

function likeBook() {
    const isbn = isbns[Math.floor(Math.random() * isbns.length)];

    const res = http.post(
        `${BASE_URL}/api/v1/books/${isbn}/like`,
        null,
        {
            headers: DEFAULT_HEADERS,
            tags: {name: '좋아요'},
        }
    );

    check(res, {
        '좋아요 정상 응답': (r) => r.status === 201 || r.status === 400,
        '좋아요 응답시간 500ms': (r) => r.timings.duration < 500,
    });
}

function unlikeBook() {
    const isbn = isbns[Math.floor(Math.random() * isbns.length)];

    const res = http.del(
        `${BASE_URL}/api/v1/books/${isbn}/like`,
        null,
        {
            headers: DEFAULT_HEADERS,
            tags: {name: '좋아요 취소'},
        }
    );

    check(res, {
        '좋아요 취소 정상 응답': (r) => r.status === 200 || r.status === 404,
        '취소 응답시간 500ms': (r) => r.timings.duration < 500,
    });
}

export function handleSummary(data) {
    const v = data.metrics.http_req_duration.values;
    const fmt = (val) => val != null ? `${val.toFixed(0)}ms` : 'N/A';

    console.log('');
    console.log('========== Load Test 완료 ==========');
    console.log(`총 요청 수    : ${data.metrics.http_reqs.values.count}`);
    console.log(`에러율        : ${(data.metrics.http_req_failed.values.rate * 100).toFixed(2)}%`);
    console.log(`TPS           : ${data.metrics.http_reqs.values.rate.toFixed(2)} req/s`);
    console.log(`응답시간 평균 : ${fmt(v.avg)}`);
    console.log(`응답시간 P50  : ${fmt(v['p(50)'])}`);
    console.log(`응답시간 P90  : ${fmt(v['p(90)'])}`);
    console.log(`응답시간 P95  : ${fmt(v['p(95)'])}`);
    console.log(`응답시간 P99  : ${fmt(v['p(99)'])}`);
    console.log('=====================================');

    return {
        'k6/results/load_summary.json': JSON.stringify(data, null, 2),
    };
}
