It is the important part of any server process or any application, 
by looking at  which we get a lot a lot of information,
especially during trouble-shooting or for some data  analysis, so for that log files are also required , but as a system admin ,either if we are 
managing  an application on a server , it is also our duty to manage those log file,because if we don't manage,then whay will happen, then files will keep
increasing, the size will keep increasing and there can be a space issue , server can go out of memory,disk can be full or many issues related to it can come up,
so that's why  log management is so important, for which we can use log rotate.

this is the default tool, so it is very easy to use it.
\practical
logrotate 
apt install logrotate
how to work ?
folder path : cd /var /log
ls -> we will see a lot of files here, let's taken an  example
boot.log --> it means currect latest log file if showing 
boot.log-20250410 -> it means old log date

how to create log files and from where is the things heppening and how is it happening ?
 
 
 the logrotate is responsible for log rotation.
 how does the log rotate?
 config files -- > logrotate main file  -- > /etc/logrotate.config 
 
 What is Logrotate? Why Use It?
🔹 What:
Logrotate is a tool in Linux that automatically manages log files — it:

Rotates (renames) old logs

Compresses them to save space

Deletes the oldest ones after a limit

🔹 Why:
Without logrotate, logs just keep growing, which can:

Fill up your disk

Slow down apps

Make it hard to find recent logs



## ❓ FAQ: Logrotate on Ubuntu

### 🔸 1. **What happens if the log file is empty?**
If the config contains `notifempty`, Logrotate **will not rotate** the log file when it's empty.

---

### 🔸 2. **Why are my rotated logs not compressed immediately?**
If you're using `delaycompress`, compression is **delayed by one rotation cycle**.  
For example:
```
myapp1.log → myapp1.log.1 (not compressed)
next run → myapp1.log.1.gz
```
This allows applications to still access the `.1` file before it's compressed.

---

### 🔸 3. **What if my app doesn't create a new log file after rotation?**
You need to use the `postrotate` script to **restart or signal your app** to reopen the log file.  
Example:
```conf
postrotate
  systemctl reload myapp.service
endscript
```

---

### 🔸 4. **How do I test my configuration safely?**
Use the dry-run mode:
```bash
sudo logrotate -d /etc/logrotate.d/myapp1
```
It simulates the rotation without making changes.

---

### 🔸 5. **How can I force rotation manually?**
Run:
```bash
sudo logrotate -f /etc/logrotate.d/myapp1
```
This bypasses schedule checks and forces a rotation immediately.

---

### 🔸 6. **Why is my log not rotating at all?**
Check these:
- File path in config is correct
- File has write permissions
- It's not empty (if `notifempty` is set)
- Cron job (or systemd timer) is running `logrotate` regularly

---

### 🔸 7. **How frequently does logrotate run?**
Logrotate runs **daily by default** via `/etc/cron.daily/logrotate` or via `systemd` timer on some systems.

---

### 🔸 8. **What does `missingok` do?**
If the specified log file doesn't exist, Logrotate will **skip it silently** instead of throwing an error.

---

### 🔸 9. **Where is the main logrotate config file?**
Located at:  
```bash
/etc/logrotate.conf
```
It includes default rules and may include:
```bash
include /etc/logrotate.d
```
Which brings in individual app configs.

---

### 🔸 10. **Can I rotate multiple log files in one config?**
Yes! You can specify multiple paths like this:
```conf
/var/log/myapp1/*.log /var/log/myapp2/access.log {
  daily
  rotate 7
  compress
  ...
}
```



sudo chown root:adm /var/log/myapp1/myapp1.log


✅ Changes the ownership of the log file to:

User: root

Group: adm (a standard group on Ubuntu for users who can read log files)

This ensures proper permissions for secure logging and log rotation.





Goal: Keep logs small, neat, and manageable — automatically!


Question | Answer
---

Kya logrotate auto run hota hai?                      | ✅ Haan, daily via cron job

Kaun sa file usko chalata hai?                        | /etc/cron.daily/logrotate

Kis config file ko use karta hai?                     | /etc/logrotate.conf (ye file /etc/logrotate.d/ ko bhi include karti hai)

Usko kaise pata chalta hai rotate karna hai ya nahi?  | /var/lib/logrotate/status file ke timestamps se

System off ho toh?                                    | anacron ensure karta hai job next boot pe chale

3. missingok
Agar log file exist nahi karti, toh error mat do.

Isse rotation fail nahi hoti agar file temporarily missing hai.

🧪 Example:

Suppose /var/log/myapp1/myapp1.log file accidentally delete ho gayi.

Jab logrotate chalta hai, tab bhi koi error nahi aayega, woh silently skip kar dega.


