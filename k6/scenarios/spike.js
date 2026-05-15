import http, { setResponseCallback } from 'k6/http';
import { check } from 'k6';
import { SharedArray } from 'k6/data';
import { BASE_URL, DEFAULT_HEADERS } from '../config.js';
import { htmlReport } from '../lib/k6-reporter.js';

setResponseCallback(http.expectedStatuses(200, 201, 400, 404));

export const options = {
    summaryTrendStats: ['avg', 'min', 'med', 'max', 'p(90)', 'p(95)', 'p(99)'],
    thresholds: {
        'http_req_duration{name:상세조회}': ['p(95)<3000', 'p(99)<5000'],
        'http_req_duration{name:좋아요}':   ['p(95)<3000', 'p(99)<5000'],
        'http_req_failed':                  ['rate<0.10'],
    },
    scenarios: {
        spike: {
            executor: 'ramping-arrival-rate',
            startRate: 200,
            timeUnit: '1s',
            stages: [
                { duration: '1m', target: 400  },
                { duration: '1m', target: 400  },
                { duration: '1m', target: 600  },
                { duration: '1m', target: 600  },
                { duration: '1m', target: 800  },
                { duration: '1m', target: 800  },
                { duration: '1m', target: 1000 },
                { duration: '1m', target: 1000 },
                { duration: '1m', target: 1200 },
                { duration: '1m', target: 1200 },
                { duration: '1m', target: 1500 },
                { duration: '2m', target: 1500 },
                { duration: '1m', target: 0    },
            ],
            preAllocatedVUs: 2000,
            maxVUs: 8000,
        },
    },
};

const isbns = new SharedArray('isbns', function () {
    return JSON.parse(open('../data/isbn_list.json'));
});

export default function () {
    const roll = Math.random();
    if (roll < 0.80)      getBookDetail();
    else if (roll < 0.95) likeBook();
    else                  unlikeBook();
}

function getBookDetail() {
    const isbn = isbns[Math.floor(Math.random() * isbns.length)];
    const res = http.get(
        `${BASE_URL}/api/v1/books/${isbn}`,
        { headers: DEFAULT_HEADERS, tags: { name: '상세조회' } }
    );
    check(res, {
        '상세조회 status 200':       (r) => r.status === 200,
        '상세조회 응답시간 1000ms':   (r) => r.timings.duration < 1000,
        '상세조회 likeCount 존재':    (r) => { try { return r.json('data.likeCount') !== undefined; } catch { return false; } },
        '상세조회 reportCount 존재':  (r) => { try { return r.json('data.reportCount') !== undefined; } catch { return false; } },
    });
}

function likeBook() {
    const isbn = isbns[Math.floor(Math.random() * isbns.length)];
    const res = http.post(
        `${BASE_URL}/api/v1/books/${isbn}/like`,
        null,
        { headers: DEFAULT_HEADERS, tags: { name: '좋아요' } }
    );
    check(res, {
        '좋아요 정상 응답':        (r) => r.status === 201 || r.status === 400,
        '좋아요 응답시간 1000ms':  (r) => r.timings.duration < 1000,
    });
}

function unlikeBook() {
    const isbn = isbns[Math.floor(Math.random() * isbns.length)];
    const res = http.del(
        `${BASE_URL}/api/v1/books/${isbn}/like`,
        null,
        { headers: DEFAULT_HEADERS, tags: { name: '좋아요 취소' } }
    );
    check(res, {
        '좋아요 취소 정상 응답':  (r) => r.status === 200 || r.status === 404,
        '취소 응답시간 1000ms':   (r) => r.timings.duration < 1000,
    });
}

export function handleSummary(data) {
    const v = data.metrics.http_req_duration.values;
    const fmt = (val) => val != null ? `${val.toFixed(0)}ms` : 'N/A';
    const detailMetric = data.metrics['http_req_duration{name:상세조회}'];
    const likeMetric   = data.metrics['http_req_duration{name:좋아요}'];

    console.log('');
    console.log('========== Spike Test 완료 ==========');
    console.log(`총 요청 수    : ${data.metrics.http_reqs.values.count}`);
    console.log(`에러율        : ${(data.metrics.http_req_failed.values.rate * 100).toFixed(2)}%`);
    console.log(`TPS           : ${data.metrics.http_reqs.values.rate.toFixed(2)} req/s`);
    console.log('');
    console.log('[전체]');
    console.log(`평균  : ${fmt(v.avg)}`);
    console.log(`P50   : ${fmt(v['p(50)'])}`);
    console.log(`P90   : ${fmt(v['p(90)'])}`);
    console.log(`P95   : ${fmt(v['p(95)'])}`);
    console.log(`P99   : ${fmt(v['p(99)'])}`);
    console.log(`Max   : ${fmt(v.max)}`);
    console.log('');
    console.log('[상세조회]');
    console.log(`P95   : ${fmt(detailMetric?.values?.['p(95)'])}`);
    console.log(`P99   : ${fmt(detailMetric?.values?.['p(99)'])}`);
    console.log('');
    console.log('[좋아요]');
    console.log(`P95   : ${fmt(likeMetric?.values?.['p(95)'])}`);
    console.log(`P99   : ${fmt(likeMetric?.values?.['p(99)'])}`);
    console.log('=====================================');

    return {
        'k6/results/spike_summary.json': JSON.stringify(data, null, 2),
        'k6/results/spike_summary.html': htmlReport(data),
    };
}
