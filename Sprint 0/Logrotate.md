## ✅ SOP: Configuring and Managing Log Rotation using Logrotate on Ubuntu

### 📌 Objective:
To configure and manage automatic log rotation on Ubuntu systems using **Logrotate**, ensuring logs are rotated periodically, retained for a defined duration, and storage is optimized.

---

### 📁 1. What is Logrotate?
**Logrotate** is a Linux utility that manages the automatic rotation and compression of log files. It prevents log files from consuming excessive disk space by rotating, compressing, and eventually deleting old logs.

---

### ⚙️ 2. Logrotate Installation
```bash
sudo apt update
sudo apt install logrotate
```
🔍 Verify installation:
```bash
logrotate --version
```
> Logrotate is usually pre-installed on Ubuntu.

---

### 📂 3. Logrotate Configuration Files

- Global Config: `/etc/logrotate.conf`
- Service-Specific Config: `/etc/logrotate.d/<service-name>`

---

### 🔧 4. Configuration Parameters

| Parameter | Description |
|----------|-------------|
| `daily` / `weekly` / `monthly` | Sets rotation frequency |
| `rotate <n>` | Number of old logs to keep |
| `compress` | Compresses old logs using gzip |
| `missingok` | Skip if log file is missing |
| `notifempty` | Don’t rotate if log is empty |
| `create` | Create a new log file after rotation |
| `dateext` | Appends date to rotated logs |
| `postrotate` / `endscript` | Run custom command/script after rotation |

---

### 📌 5. Example Use Case (Custom App Logs)
Create a config: `/etc/logrotate.d/myapp`
```conf
/var/log/myapp/myapp.log {
    daily                  # Rotate the log file daily
    rotate 5               # Keep a maximum of 5 old log files
    missingok              # Do not show an error if the log file is missing
    compress               # Compress the old log files (e.g., to .gz)
    delaycompress          # Delay compression by one rotation cycle (i.e., compress from .2 onward)
    notifempty             # Do not rotate the log file if it is empty
    create 0640 root adm   # After rotation, create a new log file with these permissions and ownership
    postrotate
        systemctl reload myapp  # After rotation, reload the "myapp" service
    endscript
}

```

---

### 🧪 6. Test Logrotate Manually
Dry Run:
```bash
sudo logrotate -d /etc/logrotate.d/myapp
```
Force Rotation:
```bash
sudo logrotate -f /etc/logrotate.d/myapp
```

---

### 📊 7. Verifying Log Rotation
After rotation, check:
```bash
ls -l /var/log/myapp/
```
Expected output:
```
app.log
app.log-2025-04-13.gz
...
```

---

### 🛠️ 8. Troubleshooting Tips

| Issue | Solution |
|-------|----------|
| Logs not rotating | Check paths, permissions |
| Rotation too frequent | Verify interval and timestamps |
| App not logging | Use `create` and `postrotate` to reopen file handles |

---

### 📦 9. Best Practices

- Use `compress` and `delaycompress` to save space.
- Always test with `-d` before deploying configs.
- Set retention based on space and compliance.
- Reload app using `postrotate` if required.

---

### 📘 References

- `man logrotate`
- https://betterstack.com/community/guides/logging/how-to-manage-log-files-with-logrotate-on-ubuntu-20-04/#getting-started-with-logrotate

