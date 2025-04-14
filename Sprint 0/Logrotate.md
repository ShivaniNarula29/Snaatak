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

    # Rotate the log file daily
    daily

    # Keep a maximum of 5 old log files
    rotate 5

    # Do not show an error if the log file is missing
    missingok

    # Compress the old log files
    compress

    # Delay compression by one rotation cycle
    delaycompress

    # Do not rotate the log file if it is empty
    notifempty

    # After rotation, create a new log file with these permissions and ownership
    # adm is a standard group for managing log file access securely.
    create 0640 root adm
}
```

---

### 🧪 6. Test Logrotate Manually
🧪 a) Dry Run (No actual change):

sudo logrotate -d /etc/logrotate.d/myapp

🧪 b) Force Rotation:

sudo logrotate -f /etc/logrotate.d/myapp

📝 Note: If the log file is empty and notifempty is set, logrotate will not rotate the file, even with -f.

✅ c) Forcibly Test Rotation When Log is Empty
To test rotation even when the log is empty, write a dummy log entry:

echo "Test log entry - $(date)" | sudo tee -a /var/log/myapp/myapp.log

Then force rotation:
sudo logrotate -f /etc/logrotate.d/myapp

This ensures the log file is non-empty, allowing rotation to proceed even with notifempty.

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

