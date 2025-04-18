
## ✅ SOP: Configuring and Managing Log Rotation using Logrotate on Ubuntu

### 📌 Objective:
To configure and manage automatic log rotation on Ubuntu systems using **Logrotate**, ensuring logs are rotated periodically, retained for a defined duration, and storage is optimized.

---

### 📁 1. What is Logrotate?
**Logrotate** is a Linux utility that manages the automatic rotation and compression of log files. It prevents log files from consuming excessive disk space by rotating, compressing, and eventually deleting old logs.

---

## ⚙️ Step 1: Install Logrotate (if not already installed)

```bash
sudo apt update
sudo apt install logrotate
```

Check the version:

```bash
logrotate --version
```

---

## 📁 Step 2: Create Log File for Your App

Let’s assume your app writes logs to `/var/log/myapp1/myapp1.log`.

```bash
sudo mkdir -p /var/log/myapp1
sudo touch /var/log/myapp1/myapp1.log
sudo chown root:adm /var/log/myapp1/myapp1.log
```

---

## 🛠️ Step 3: Create Logrotate Config

Create a file at `/etc/logrotate.d/myapp1`

```bash
sudo nano /etc/logrotate.d/myapp1
```

Paste the following configuration:

```conf
/var/log/myapp1/myapp1.log {

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

✅ This configuration:
- Rotates log **daily**
- Keeps last **5 logs**
- Compresses old logs
- Skips rotation if log is empty
- Recreates the log file with correct permission

---

## 🧪 Step 4: Test the Setup

### a) Add a test log entry

```bash
echo "Test log entry - $(date)" | sudo tee -a /var/log/myapp1/myapp1.log
```

### b) Simulate Log Rotation (Dry Run)

```bash
sudo logrotate -d /etc/logrotate.d/myapp1
```

🧪 This will show what would happen without actually rotating.

### c) Force Log Rotation

```bash
sudo logrotate -f /etc/logrotate.d/myapp1
```

Now check:

```bash
ls -l /var/log/myapp1/
```

You should see something like:

```
myapp1.log
myapp1.log.1
```

---

## 🔁 Step 5: Add More Logs and Rotate Again

```bash
echo "Another log entry - $(date)" | sudo tee -a /var/log/myapp1/myapp1.log
sudo logrotate -f /etc/logrotate.d/myapp1
```

Check again:

```bash
ls -l /var/log/myapp1/
```

Expected output (with rotation and compression enabled):

```
myapp1.log
myapp1.log.1.gz
myapp1.log.2.gz
...
```

---
### 🛠️ Troubleshooting Tips

| Issue | Solution |
|-------|----------|
| Logs not rotating | Check paths, permissions |
| Rotation too frequent | Verify interval and timestamps |
| App not logging | Use `create` and `postrotate` to reopen file handles |
| Rotation skipped	| Ensure log is not empty if notifempty is set |

---

### 📦 Best Practices

- Use `compress` and `delaycompress` to save space.
- Always test with `-d` before deploying configs.
- Set retention based on space and compliance.
- Reload app using `postrotate` if required.

---

### 📘 References

- `man logrotate`
- https://betterstack.com/community/guides/logging/how-to-manage-log-files-with-logrotate-on-ubuntu-20-04/#getting-started-with-logrotate

---
## 🛑 Common Issues

| Problem | Fix |
|--------|------|
| Log not rotating | Check path and permission |
| Rotation not triggered | Use `-f` to force |
| Rotation skipped | File may be empty (notifempty) |
| App not logging anymore | App might need to reopen log file after rotation |

---

## 📘 Reference

- `man logrotate`
- https://betterstack.com/community/guides/logging/how-to-manage-log-files-with-logrotate-on-ubuntu-20-04/
