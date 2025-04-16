# 🐍 Gunicorn Intro Documentation

| Created | Version | Author | Comment | Reviewer |
| --- | --- | --- | --- | --- |
| 15-04-2025 | V1 | Shivani Narula |  | Siddharth Pawar |

---

![image](https://github.com/user-attachments/assets/890340a1-ed5e-4add-bdbb-3c49bd099890)
                                
### ✅ What is Gunicorn?

The standard web servers such as Apache, and NGINX don’t know how to communicate with your Python applications. Web servers receive the request from a client(Web browser) and return a response. The web server doesn’t create the response, it only returns the response. So, a server needs to talk with a Web Application that can create a response.

And what Web Application can do? Anything your project needs it to do. I am sure you all have cool ideas to release to the world. We almost live on the web these days. So, what you build will need to communicate to the web servers in order to reach your users/audience over the internet. Therefore, we need an architecture, sort of a protocol, everyone agrees on, to bridge the request-response cycle between your web server and web application.

WSGI comes into the picture because it basically provides that bridge of your need to communicate between your Web Server and Web Application. WSGI (Web Server Gateway Interface), is a set of rules which allow a WSGI compliant server to work with a WSGI compliant Python application. WSGI also handles scaling for web servers to be able to handle thousands of requests so you don’t have to think about accepting multiple requests at a time.

**Gunicorn** (Green Unicorn) is a **production-grade WSGI HTTP server** designed to serve Python web applications. It acts as a bridge between your web application (built with frameworks like **Django**, **Flask**, or **FastAPI**) and a **web server** such as **Nginx** or **Apache**.


![image](https://github.com/user-attachments/assets/77554584-461e-440a-8fc3-2c678baacd9b)


### 🔍 Key Role (From the Diagram Above)

From the diagram, you can see:
- **Your Python App** doesn’t talk directly to the internet.
- **Gunicorn** runs your app and manages **multiple worker processes**.
- It passes responses to **Nginx**, which delivers them to users.

In simpler terms, Gunicorn helps:
- Launch your Python web app for public or internal access.
- Handle traffic smoothly even when multiple users access the app.
- Work seamlessly with other tools (like Nginx) for speed and security.

---

### 🔧 Prerequisites
Before getting started with Gunicorn, make sure your system has the following:

- The latest version of Python (3.13.2 or higher)
- pip, Python’s package manager (included with Python 3.x)
- Experience with a Python web framework like Flask for testing Gunicorn

---

### ❓ Why Use Gunicorn?

Python web frameworks come with development servers that are:
- Not optimized for **performance**
- Not suitable for **concurrent user handling**
- Lacking in **logging**, **graceful restarts**, and **worker monitoring**

Gunicorn addresses these issues and provides a production-ready deployment. Here's why it's widely used:

| Reason               | Explanation                                                                                      |
|----------------------|--------------------------------------------------------------------------------------------------|
| 🚀 **Performance**     | Uses multiple worker processes for high throughput and fast responses.                |
| ⚙️ **WSGI Compliance** | Fully compatible with WSGI standard; supports any Python web framework.              |
| 🔁 **Concurrency**     | Handles multiple simultaneous requests with sync or async workers.                  |
| 🔒 **Security**         | Can be placed behind Nginx for SSL, DDoS protection, etc.                           |
| 🔄 **Reliability**      | Monitors workers, restarts on failure, and supports graceful restarts.              |
| 🔧 **Customizable**     | Easy configuration for workers, timeouts, logs, IP bindings, and more.             |
| 📦 **Lightweight**      | Fast startup and small memory footprint.                                            |

---

### 🌟 Key Features of Gunicorn

| **Feature**                       | **What It Does / Why It Matters**                                                                                             |
|----------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| **Pre-Fork Worker Model**        | Spawns multiple workers ahead of time; each handles requests in parallel.                                                     |
| **Multiple Worker Types**        | Supports `sync`, `gevent`, `eventlet`, and threaded models.                                                                   |
| **Simple Command-Line Usage**    | Start the server using: `gunicorn app:app` or use config/env files.                                                          |
| **Port Binding & Interfaces**    | Binds to custom IP/port for internal or public access.                                                                        |
| **Graceful Worker Restarts**     | Avoids downtime with hot reload: `kill -HUP <pid>`                                                                            |
| **Logging & Monitoring**         | Logs access/errors and supports monitoring hooks.                                                                             |
| **Reverse Proxy Compatibility**  | Works behind Nginx for SSL, load balancing, and static file handling.                                                        |
| **Flexible Scaling**             | Scale vertically or horizontally with ease.                                                                                   |
| **Cross-Platform (UNIX)**        | Built for UNIX-based systems (Linux, macOS).                                                                                  |
| **Lightweight Runtime**          | Minimal resource usage; ideal for container environments.                                                                     |

---

🧩 Real-World Scenario

🔸Imagine you're building a school management system using Flask.

Initially, you test it using Flask’s built-in server (flask run). But once your school deploys it for real usage, the site becomes slow and sometimes crashes when many teachers/students log in at once.

✅ To fix that:

You deploy Gunicorn to handle concurrent users.

You use 4 worker processes, so multiple requests can be processed at the same time.

You add Nginx in front to serve static files and manage SSL.

Your site becomes faster, more reliable, and secure.

🎯 Conclusion: Gunicorn acts like a team of helpers running your app in the background, while Nginx is the receptionist talking to users.

---

### 🧠 In Summary:

Gunicorn is a **critical part** of deploying Python web applications. It transforms your app from a simple script to a **reliable, concurrent, and production-grade service**.  

It enables:

 🔹 High concurrency

 🔹 Smooth integration with reverse proxies

 🔹 Fast and safe deployments

 🔹 Secure and reliable operations

---

### 📘 References

- https://en.wikipedia.org/wiki/Gunicorn#
- https://docs.gunicorn.org/en/stable/
- https://gunicorn.org/
- [https://betterstack.com/community/guides/logging/how-to-manage-log-files-with-logrotate-on-ubuntu-20-04/#getting-started-with-logrotate](https://medium.com/@serdarilarslan/what-is-gunicorn-5e674fff131b)

---

## 📇 Contacts

| Name | Email Address |
| --- | --- |
| Shivani Narula | shivani.narula.snaatak@mygurukulam.co |
