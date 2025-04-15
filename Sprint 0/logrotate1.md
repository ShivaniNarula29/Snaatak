
# ✅ Logrotate Setup on Ubuntu (Beginner Friendly)

This guide will help you configure **automatic log rotation** on Ubuntu using Logrotate. This is useful to prevent log files from growing too large.

---

## 📌 What is Logrotate?

**Logrotate** is a tool used to manage and rotate log files automatically. It helps:
- Prevent logs from filling up the disk
- Compress old logs
- Keep only a limited number of backups

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
sudo chown root:root /var/log/myapp1/myapp1.log
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
    daily
    rotate 5
    missingok
    compress
    delaycompress
    notifempty
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
