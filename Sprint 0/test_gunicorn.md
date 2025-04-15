

from pathlib import Path

# Updated README.md content
readme_content = """
# Gunicorn Intro Documentation

## 🐍 Common Stack | Application | Python | Gunicorn

| Created     | Version | Author          | Comment | Reviewer         |
|-------------|---------|------------------|---------|------------------|
| 14-04-2025  | V1      | Shivani Narula   |         | Siddharth Pawar  |

---

![Gunicorn in Stack](https://github.com/user-attachments/assets/890340a1-ed5e-4add-bdbb-3c49bd099890)

---

## ✅ What is Gunicorn?

**Gunicorn** (Green Unicorn) is a **production-grade WSGI HTTP server** designed to serve Python web applications. It acts as a **bridge** between your Python code (typically using frameworks like **Flask**, **Django**, or **FastAPI**) and a web server like **Nginx** or **Apache**.

Gunicorn is essential when you want your app to be **accessible to multiple users** in a **real-world environment**.

![Gunicorn Workflow](https://github.com/user-attachments/assets/77554584-461e-440a-8fc3-2c678baacd9b)

### 🔍 Key Role (From the Diagram Above)

From the diagram, you can see:
- **Your Python App** doesn’t talk directly to the internet.
- **Gunicorn** runs your app and manages **multiple worker processes**.
- It passes responses to **Nginx**, which delivers them to users.

---

## 🔧 Prerequisites

Before you use Gunicorn, ensure the following:
- Python 3.13.2 or higher installed.
- `pip` (Python package manager).
- A basic app using a Python framework (Flask/Django/FastAPI).

Install Gunicorn using:
```bash
pip install gunicorn
