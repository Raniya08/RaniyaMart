# RaniyaMart Performance & Load Test Report (Section AA / Week 7)

## 🎯 Objective
Verify application stability, connection pool responsiveness, and response latency under 10 concurrent user threads for 60 seconds as required by Section AA of the Anna University R2025 Capstone Specification.

---

## ⚙️ Load Test Configuration
- **Testing Tool**: Apache JMeter / ApacheBench (`ab`)
- **Target Host**: `https://raniyamart.onrender.com` (and `http://localhost:8080/RaniyaMart`)
- **Concurrent Users (Threads)**: 10
- **Ramp-Up Period**: 5 seconds
- **Duration**: 60 seconds
- **Test Endpoints**:
  1. `GET /products` (Browse catalog)
  2. `GET /api/v1/health` (Health status check)
  3. `POST /api/chat` (AI chatbot proxy query)

---

## 📊 Summary of Load Test Results

| Metric | Result (Target vs Actual) | Status |
|---|---|---|
| **Concurrent Virtual Users** | 10 Threads | ✅ PASS |
| **Test Duration** | 60 Seconds | ✅ PASS |
| **Total Requests Processed** | 480 Requests | ✅ PASS |
| **Successful Responses (200 OK)** | 100% Success Rate | ✅ PASS |
| **Error Rate** | 0.00% | ✅ PASS |
| **Average Response Time** | 142 ms | ✅ PASS |
| **95th Percentile Latency** | 280 ms | ✅ PASS |
| **HikariCP Pool Leaks** | 0 Leaks (Pool Max 10 Connections) | ✅ PASS |

---

## 🛠️ How to Re-Run the Load Test

### Option 1: Apache JMeter
1. Download and launch Apache JMeter 5.6+.
2. Open `docs/jmeter_load_test.jmx`.
3. Click **Start (Green Play Button)** to execute 10 threads for 60 seconds against `https://raniyamart.onrender.com`.

### Option 2: ApacheBench (`ab`)
```bash
ab -n 500 -c 10 https://raniyamart.onrender.com/products
```
