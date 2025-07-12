## **Redis Middleware Monitoring – Prometheus + Grafana**

<p align="center">
<img src="https://shalb.com/wp-content/uploads/2022/09/Grafana.png" width="400" height="200"/>
</p>

---

## **Author Information**

| Created    | Last updated | Version | Author         | Level           | Reviewer      |
| ---------- | ------------ | ------- | -------------- | --------------- | ------------- |
| 12‑07‑2025 | 12‑07‑2025   | V1.0    | Shivani Narula | Internal Review | Aman          |
| 12‑07‑2025 | 12‑07‑2025   | V1.0    | Shivani Narula | L0 Review       | Raj Kumar     |
| 12‑07‑2025 | 12‑07‑2025   | V1.0    | Shivani Narula | L1 Review       | Deepak Nishad |

---

## Table of Contents

* [Introduction](#introduction)
* [Prerequisites](#prerequisites)
* [Step-by-Step Instructions](#step-by-step-instructions)

  * [Step 1 – Install Redis Exporter](#step-1)
  * [Step 2 – Configure Prometheus](#step-2)
  * [Step 3 – Redis Alert Rules](#step-3)
  * [Step 4 – Alertmanager Email Setup](#step-4)
  * [Step 5 – Connect Prometheus to Grafana](#step-5)
  * [Step 6 – Create Redis Dashboard](#step-6)
* [Conclusion](#conclusion)
* [Reference Table](#reference-table)

---

## Introduction

This POC demonstrates Redis middleware monitoring using Prometheus, Redis Exporter, Grafana, and Alertmanager. It enables real-time Redis metric collection, visualization via Grafana dashboards, and email alerting through Alertmanager.

---

## Prerequisites

| Item              | Description                                 |
| ----------------- | ------------------------------------------- |
| Monitoring Server | Prometheus, Grafana, Alertmanager installed |
| Redis Target      | Redis + Redis Exporter installed            |
| SMTP Access       | Gmail app password or corporate SMTP relay  |

---

## Step-by-Step Instructions

<a id="step-1"></a>

### **Step 1: Check Versions and Install if Not Installed**

```bash
prometheus --version
alertmanager --version
grafana-server -v
```

<img width="1447" height="481" alt="image" src="https://github.com/user-attachments/assets/ba858798-77ad-493c-8a7b-5f0b0847bae8" />

---

<a id="step-2"></a>

### **Step 2 – Point Prometheus at Redis Exporter**

```bash
/etc/prometheus/prometheus.yml
```

<img width="1160" height="648" alt="image" src="https://github.com/user-attachments/assets/67ee71d2-049a-4e4f-b17d-5533994beb8d" />

* Make sure Redis Exporter is installed on the target server.

---

<a id="step-3"></a>

### **Step 3 – Add alert rules**

```bash
/etc/prometheus/redis-rules.yml
```

<img width="1631" height="354" alt="Screenshot 2025-07-12 131419" src="https://github.com/user-attachments/assets/c86a9c48-fab8-4e73-9c3d-6f8ba035b3f2" />

---

<a id="step-4"></a>

### **Step 4 – Configure Alertmanager email**

```bash
/etc/alertmanager/alertmanager.yml
```

<img width="1245" height="471" alt="image" src="https://github.com/user-attachments/assets/ea5d1a1c-7c11-433d-85a1-01544f9242fa" />

---

<a id="step-5"></a>

### **Step 5 – Add Prometheus to Grafana**

1. Go to `http://<monitoring-ip>:3000`
2. Login: `admin / admin` (default)
3. Navigate to:

   * ⚙️ **Configuration** → **Data Sources**
   * Click **Add data source** → Select **Prometheus**
4. Set the URL to:

```
http://localhost:9090
```

---

<a id="step-6"></a>

### **Step 6 – Create Redis Dashboard**

1. Go to ➕ → Import
2. Use Dashboard ID: `763`
3. Set data source: `Prometheus`

#### 🔧 Example Panels:

| Panel                         | Query (Code mode)                                                                 | Unit | Thresholds |
| ----------------------------- | --------------------------------------------------------------------------------- | ---- | ---------- |
| **Redis Memory Used (MB)**    | `redis_memory_used_bytes{instance=~"$instance"} / 1024 / 1024`                    | MB   | Red at 100 |

<img width="1900" height="852" alt="image" src="https://github.com/user-attachments/assets/6bb946fc-297a-4882-a34f-2c3c03072737" />


#### Grafana Panel Settings:

**For Redis Memory Panel (Gauge or Time Series):**

* Visualization → `Gauge` or `Time Series`
* Unit → `megabytes (MB)` (set via Field tab → Unit dropdown → Data → Bytes → MB))
* Threshold → Red at `100`
* Min = `0`
* Legend → `{{ instance }}`

#### Final Result:

<img width="1908" height="816" alt="Screenshot 2025-07-12 153943" src="https://github.com/user-attachments/assets/9049b2e8-fa3c-4d17-8860-82ed1cc88f64" />

---

### Verify Alert Triggering After a CPU or Redis Event

After 2 minutes:

* Prometheus ▸ `/alerts` → Alert status = **Firing**
* Alertmanager UI displays alert
* Email is triggered (if configured)

<img width="1882" height="491" alt="image" src="https://github.com/user-attachments/assets/cb4e4276-7ee0-49da-a2cb-51223b42acd0" />
<img width="1450" height="274" alt="image" src="https://github.com/user-attachments/assets/420d3617-d173-4084-a658-adf1a67bf4e0" />
<img width="1315" height="610" alt="Screenshot 2025-07-12 133114" src="https://github.com/user-attachments/assets/0dff9c7c-80a5-4dc6-9f0b-33791ae0c37d" />

---

## Conclusion

This POC enables quick infrastructure monitoring with Prometheus, Grafana, and Alertmanager. It covers collection, alerting, and visualization of system metrics using a reusable pattern. The same setup can be extended to monitor disk, network, and uptime as needed.

---

## Reference Table

| Link                                                                          | Purpose                         |
| ----------------------------------------------------------------------------- | ------------------------------- |
| [Prometheus Docs](https://prometheus.io/docs/)                                | Metric collection & alert rules |
| [Grafana Docs](https://grafana.com/docs/)                                     | Dashboard creation              |
| [Alertmanager Docs](https://prometheus.io/docs/alerting/latest/alertmanager/) | Email / Slack routing           |
