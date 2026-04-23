# **Technical Requirements Document (TRD)**

**Project Name:** Smart Stock Tracker (Microservices)

**Deskripsi:** Sistem pemantauan stok barang dengan fitur keamanan, cache, sinkronisasi harga via API eksternal, dan notifikasi real-time.

## **1\. Arsitektur Sistem (High-Level)**

Sistem menggunakan arsitektur Microservices dasar:

* **API Gateway (Port 8080):** Pintu masuk utama, menangani otentikasi (JWT).  
* **Service Registry / Eureka (Port 8761):** Mencatat daftar service yang aktif.  
* **Auth Service (Port 8081):** Menangani login dan generate JWT.  
* **Stock Service (Port 8082):** Menangani logika utama (CRUD Produk, Feign ke API Eksternal, Redis, Scheduler, WebSocket/SSE).

## **2\. Tech Stack**

* **Bahasa:** Java (OOP, JDK 17/21)  
* **Framework:** Spring Boot 3.x, Spring Cloud  
* **Database:** H2 Database (In-Memory) / MySQL  
* **Caching:** Redis  
* **Komunikasi Antar Service:** OpenFeign  
* **Real-time:** WebSocket & Server-Sent Events (SSE)  
* **Security:** Spring Security & JWT

## **3\. Database Schema (JPA Entities)**

### **Table: users**

| Field | Type | Constraint |
| :---- | :---- | :---- |
| id | Long | PK, Auto Increment |
| username | String | Unique, Not Null |
| password | String | Encrypted |

### **Table: products**

| Field | Type | Constraint |
| :---- | :---- | :---- |
| id | Long | PK, Auto Increment |
| name | String | Not Null |
| price | Double | Not Null, Min(0) |
| stock | Integer | Not Null, Min(0) |

## **4\. Development Roadmap (Sesuai Silabus)**

### **Day 1 & 2: Setup & CRUD Dasar**

* Inisialisasi Stock Service.  
* Buat struktur folder: controller, service, repository, model/entity, dto.  
* Implementasi JPA CRUD untuk entitas Product.

### **Day 3: Validasi, Exception, & External API**

* Tambahkan anotasi @Valid pada DTO (contoh: @Min(0) untuk harga).  
* Buat @RestControllerAdvice (Global Exception Handler) untuk menangani error validasi dan ProductNotFoundException.  
* Implementasi **Feign Client** untuk memanggil API publik (misal: FreeCurrencyAPI) guna menampilkan estimasi harga dalam USD.

### **Day 4: Microservices Setup & Token**

* Buat service baru: Service Registry (Eureka Server). Daftarkan Stock Service ke Eureka.  
* Buat Auth Service. Implementasikan logika pembuatan **JWT** (JSON Web Token) saat user login.

### **Day 5: Gateway & Security**

* Buat service baru: API Gateway. Arahkan routing /api/stock/\*\* ke Stock Service dan /api/auth/\*\* ke Auth Service.  
* Tambahkan filter **Spring Security** di Gateway untuk memvalidasi token JWT pada setiap request (kecuali endpoint login).

### **Day 6: Performa & Otomatisasi**

* Pasang **Redis**. Tambahkan anotasi @Cacheable pada method getProducts() agar akses data lebih cepat tanpa membebani database.  
* Buat class dengan anotasi @Scheduled (cron job) yang berjalan setiap 1 jam untuk mengecek produk dengan stok \< 5, lalu print log di console.

### **Day 7: Real-Time Features**

* **SSE (Server-Sent Events):** Buat endpoint /api/stock/alerts yang mengirim aliran data (stream) satu arah ke client jika ada peringatan stok menipis.  
* **WebSocket:** Buat koneksi dua arah. Setiap kali admin melakukan update harga barang via REST API, broadcast perubahan harga tersebut ke semua client yang terhubung via WebSocket.

## **5\. Daftar API Endpoints Utama**

### **Auth Service (Port 8081\)**

* POST /auth/login \-\> Request: {username, password} | Response: {token}

### **Stock Service (Port 8082\) \- Diakses via Gateway**

* GET /stock/products \-\> Ambil semua produk (Gunakan Redis Cache).  
* POST /stock/products \-\> Tambah produk (Wajib Header JWT).  
* GET /stock/products/{id}/usd \-\> Ambil detail produk \+ harga USD (Gunakan Feign Client).  
* GET /stock/alerts \-\> Endpoint SSE untuk notifikasi.