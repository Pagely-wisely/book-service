#!/bin/bash

BASE_URL=${1:-"http://localhost:19051"}
SCENARIO=${2:-"load"}

mkdir -p k6/results

START_TIME=$(date '+%Y-%m-%d %H:%M:%S')
START_TIMESTAMP=$(date '+%s')

echo "=============================="
echo "테스트 대상  : $BASE_URL"
echo "시나리오     : $SCENARIO"
echo "시작 시간    : $START_TIME"
echo "=============================="

case $SCENARIO in
  smoke)
    echo ""
    echo "[Smoke Test 시작]"
    echo "완료 후 k6/data/isbn_list.json을 준비해주세요."
    echo ""
    k6 run -e BASE_URL=$BASE_URL k6/scenarios/smoke.js
    ;;

  load)
    if [ ! -f "k6/data/isbn_list.json" ]; then
        echo "isbn_list.json이 없어요. Smoke Test 먼저 실행해주세요."
        exit 1
    fi
    echo ""
    echo "[Load Test 스테이지 계획]"
    echo "  Stage 1  (5분)  0   → 100"
    echo "  Stage 2  (5분)  100 유지"
    echo "  Stage 3  (1분)  복구 확인"
    echo "  총 11분"
    echo ""
    echo "[Load Test 시작]"

    (
        sleep 300; echo "  [$(date '+%H:%M:%S')] Stage 2  100 유지"
        sleep 300; echo "  [$(date '+%H:%M:%S')] Stage 3  복구 확인"
    ) &
    STAGE_PID=$!

    k6 run -e BASE_URL=$BASE_URL k6/scenarios/load.js

    kill $STAGE_PID 2>/dev/null
    ;;

  stress)
    if [ ! -f "k6/data/isbn_list.json" ]; then
        echo "isbn_list.json이 없어요. Smoke Test 먼저 실행해주세요."
        exit 1
    fi
    echo ""
    echo "[Stress Test 스테이지 계획]"
    echo "  Stage 1  (1분)  0    → 100"
    echo "  Stage 2  (1분)  100  유지"
    echo "  Stage 3  (1분)  100  → 300"
    echo "  Stage 4  (1분)  300  유지"
    echo "  Stage 5  (1분)  300  → 500"
    echo "  Stage 6  (1분)  500  유지"
    echo "  Stage 7  (1분)  500  → 700"
    echo "  Stage 8  (1분)  700  유지"
    echo "  Stage 9  (1분)  700  → 1000"
    echo "  Stage 10 (1분)  1000 유지"
    echo "  Stage 11 (1분)  1000 → 1500"
    echo "  Stage 12 (2분)  1500 유지"
    echo "  Stage 13 (1분)  복구 확인"
    echo "  총 15분"
    echo ""
    echo "[Stress Test 시작]"

    (
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 2  100 유지"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 3  100 → 300"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 4  300 유지"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 5  300 → 500"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 6  500 유지"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 7  500 → 700"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 8  700 유지"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 9  700 → 1000"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 10 1000 유지"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 11 1000 → 1500"
        sleep 60;  echo "  [$(date '+%H:%M:%S')] Stage 12 1500 유지 (2분)"
        sleep 120; echo "  [$(date '+%H:%M:%S')] Stage 13 복구 확인"
    ) &
    STAGE_PID=$!

    k6 run -e BASE_URL=$BASE_URL k6/scenarios/stress.js

    kill $STAGE_PID 2>/dev/null
    ;;

  *)
    echo "사용법: ./k6/run.sh [BASE_URL] [smoke|load|stress]"
    exit 1
    ;;
esac

END_TIME=$(date '+%Y-%m-%d %H:%M:%S')
END_TIMESTAMP=$(date '+%s')
DURATION=$((END_TIMESTAMP - START_TIMESTAMP))

echo ""
echo "=============================="
echo "시작 시간 : $START_TIME"
echo "종료 시간 : $END_TIME"
echo "총 소요   : ${DURATION}초"
echo "결과 파일 : k6/results/${SCENARIO}_summary.json"
echo "=============================="

cat > k6/results/${SCENARIO}_timeline.json << EOF
{
  "scenario": "$SCENARIO",
  "base_url": "$BASE_URL",
  "start_time": "$START_TIME",
  "end_time": "$END_TIME",
  "duration_seconds": $DURATION
}
EOF

echo "타임라인 저장: k6/results/${SCENARIO}_timeline.json"
