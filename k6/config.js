export const BASE_URL = __ENV.BASE_URL || 'http://localhost:19051';

export const DEFAULT_HEADERS = {
    'Content-Type': 'application/json',
    'X-User-Id': '00000000-0000-0000-0000-000000000001',
    'X-User-Role': 'USER',
};

export const SMOKE_OPTIONS = {
    vus: 5,
    duration: '2m',
    thresholds: {
        http_req_failed: ['rate<0.05'],
    },
};

export const LOAD_OPTIONS = {
    thresholds: {
        'http_req_duration{name:상세조회}': ['p(50)<300', 'p(95)<500', 'p(99)<1000'],
        'http_req_duration{name:좋아요}': ['p(50)<200', 'p(95)<500', 'p(99)<1000'],
        'http_req_failed': ['rate<0.01'],
    },
    scenarios: {
        load: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                {duration: '5m', target: 100},
                {duration: '5m', target: 100},
                {duration: '1m', target: 0},
            ],
        },
    },
};
