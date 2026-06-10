_**Version 1**_ 

_Last Updated 30/March/2026_ 

## **Product Requirements Document** 

**PROJECT POJOK TEDUH** 

|**Project Manager**|Aisyah, Sisil|
|---|---|
|**Team**|5|
|**Summary**||
|**Status**|Planning|
|**Next Milestone**||



© 2026 Veteran Tech | All right reserved | 1 

## **List of Contents** 

**1. Backgrounds.........................................................................................................................................2** 1.1. Problem Statements...............................................................................................................................................2 1.2. Goals..........................................................................................................................................................................2 1.3. Solution......................................................................................................................................................................2 **2. User Persona........................................................................................................................................ 3** 2.1. Mahasiswa................................................................................................................................................................ 3 2.2. Pekerja Kantoran/Freelancer...............................................................................................................................4 **3. User Story & Requirement...................................................................................................................4** 3.1. User............................................................................................................................................................................4 3.2. Administrator.......................................................................................................................................................... 6 **4. High-Level Architecture......................................................................................................................7** 4.1. High-Level Architecture........................................................................................................................................7 **5. Sitemap.................................................................................................................................................7** 5.1. Landing Page........................................................................................................................................................... 7 5.2. Dashboard User......................................................................................................................................................7 5.3. Dashboard Admin..................................................................................................................................................8 **6. Detailed Requirements - Landing Page.............................................................................................. 8 7. Detailed Requirements - Sign Up Page............................................................................................. 10 8. Detailed Requirements - Dashboard User Page............................................................................... 11 9. Detailed Requirements - Spot Management Admin Page................................................................13 10. Detailed Requirements - Dashboard Admin Page..........................................................................14 11. Sprint Roadmap.................................................................................................................................15** 11.1. Sprint 1 (5 April – 12 April 2026)....................................................................................................................... 15 11.2. Sprint 2 (11 April – 19 April 2026).....................................................................................................................17 11.3. Sprint 3 (20 April – 29 April 2026)................................................................................................................... 18 

© 2026 Veteran Tech | All right reserved | 2 

## **1. Backgrounds** 

## **1.1. Problem Statements** 

Mahasiswa dan pekerja urban di perkotaan saat ini menghadapi tantangan besar dalam menemukan ruang publik yang kondusif untuk produktivitas maupun relaksasi mental di tengah kebisingan kota yang tinggi. Aplikasi navigasi yang ada saat ini hanya menyajikan lokasi berdasarkan popularitas dan aspek komersial, tanpa memberikan informasi detail mengenai tingkat ketenangan ( _acoustic comfort_ ) dan kenyamanan lingkungan. Akibatnya, banyak pengguna sering mendatangi lokasi yang ternyata terlalu bising, penuh sesak, atau tidak memiliki fasilitas pendukung seperti WiFi dan area teduh, sehingga menghambat fokus kerja dan meningkatkan tingkat stres akibat polusi suara perkotaan. 

## **1.2. Goals** 

Dengan dikembangkannya PojokTeduh Website & Admin Dashboard, diharapkan: 

1. Tersedianya direktori ruang publik yang mengutamakan informasi ketenangan dan kenyamanan bagi mahasiswa dan pekerja. 

2. Memudahkan pengguna menemukan "oase tersembunyi" di perkotaan melalui visualisasi peta interaktif yang terorganisir. 

3. Menyediakan sistem filter yang akurat berdasarkan fasilitas. 

4. Menjaga kredibilitas data keramaian melalui laporan langsung dari pengguna secara _real-time_ . 

5. Meningkatkan produktivitas dan kesehatan mental pengguna melalui akses informasi ruang terbuka yang teduh. 

6. Membantu digitalisasi data lokasi publik di wilayah Jakarta dan Depok yang selama ini belum terdata tingkat ketenangannya. 

## **1.3. Solution** 

1. Menyediakan Official Website PojokTeduh sebagai portal pemetaan ruang tenang berbasis web yang responsif. 

2. Menyediakan Dashboard Admin yang simpel untuk mengelola database lokasi, kategori, dan koordinat secara terpusat. 

3. Fitur Filter untuk menyaring lokasi berdasarkan kebutuhan fasilitas spesifik pengguna. 

4. Sistem Satisfaction Score  yang memungkinkan perubahan skor kepuasan secara otomatis berdasarkan _feedback_ dari user. 

© 2026 Veteran Tech | All right reserved | 3 

5. Integrasi Direct Navigation ke aplikasi pihak ketiga (Google Maps/Waze) untuk efisiensi mobilitas pengguna ke lokasi tujuan. 

## **2. User Persona** 

## **2.1. Mahasiswa** 

© 2026 Veteran Tech | All right reserved | 4 

## **2.2. Pekerja Kantoran/Freelancer** 

## **3. User Story & Requirement** 

## **3.1. User** 

|**User**|||||
|---|---|---|---|---|
|**No.**|**Title**|**User Story**|**Importance**|**Notes**|
|1.|Account<br>Auth & Profile|Sebagai<br>pengguna,<br>saya<br>ingin<br>mendaftar dan masuk ke akun agar<br>data favorit dan riwayat ulasan saya<br>tersimpan secara pribadi.|High|On Revi…|
|2.|Top<br>Recommend<br>ation|Sebagai<br>pengguna,<br>saya<br>ingin<br>melihat tempat rekomendasi (Top<br>Satisfacion Picks) dan tempat yang<br>terakhir kali saya lihat.|High|On Revi…|
|3.|Search &<br>Filter|Sebagai<br>pengguna,<br>saya<br>ingin<br>menyaring<br>tempat<br>berdasarkan<br>suasana (Fokus/Santai) dan fasilitas|High|On Revi…|



© 2026 Veteran Tech | All right reserved | 5 

|||agar<br>mendapatkan<br>hasil<br>yang<br>akurat.|||
|---|---|---|---|---|
|4.|Card Spot<br>Insight|Sebagai<br>pengguna,<br>saya<br>ingin<br>melihat<br>deskripsi<br>lengkap,<br>foto<br>suasana, dan jam operasional suatu<br>tempat agar saya punya ekspektasi<br>yang jelas sebelum berangkat.|high|On Revi…|
|5.|Satisfaction<br>Score|Sebagai<br>pengguna,<br>saya<br>ingin<br>memilih skala 1–5 untuk menilai<br>tingkat kepuasan dari suatu lokasi,<br>sehingga<br>status<br>lokasi<br>dapat<br>diperbarui secara otomatis bagi<br>pengguna lain.|Medium|On Revi…|
|6.|Rating &<br>Review<br>System|Sebagai<br>pengguna,<br>saya<br>ingin<br>memberikan<br>rating bintang dan<br>ulasan<br>tertulis<br>mengenai<br>pengalaman<br>saya<br>agar<br>bisa<br>membantu pengguna lain memilih<br>tempat terbaik.|Medium|On Revi…|
|7.|Personal<br>Saved Spot|Sebagai pengguna terdafar, saya<br>ingin menyimpan (bookmark) lokasi<br>ke<br>dafar<br>favorit<br>agar<br>dapat<br>mengakses kembali tempat-tempat<br>pilihan saya dengan cepat.|Medium|On Revi…|
|8.|Direct<br>Navigation|Sebagai<br>pengguna,<br>saya<br>ingin<br>tombol "Go There" yang langsung<br>membuka aplikasi navigasi eksternal<br>(Google Maps) agar perjalanan saya<br>lebih efsien.|High|On Revi…|
||History|Sebagai<br>pengguna,<br>saya<br>ingin|Medium|On Revi…|



© 2026 Veteran Tech | All right reserved | 6 

||Review|melihat riwayat ulasan dan penilaian<br>yang pernah saya berikan, agar saya<br>dapat<br>mengingat<br>kembali<br>pengalaman saya serta mengelola<br>kontribusi<br>saya<br>terhadap<br>suatu<br>lokasi.|||
|---|---|---|---|---|



## **3.2. Administrator** 

|**No.**|**Title**|**User Story**|**Importance**|**Notes**|
|---|---|---|---|---|
|1.|Secure<br>Admin Login|Sebagai admin, saya ingin masuk ke<br>dashboard khusus melalui sistem<br>login yang aman agar data lokasi<br>tidak bisa diubah oleh sembarang<br>orang.|High|On Revi…|
|2.|Spot<br>Management<br>(CRUD)|Sebagai<br>admin,<br>saya<br>ingin<br>menambah,<br>mengedit,<br>atau<br>menghapus titik lokasi (koordinat,<br>nama, kategori) agar dafar tempat<br>di peta selalu akurat.|High|On Revi…|
|3.|Auto-<br>Satisfaction<br>Score Logic|Sebagai admin, saya ingin sistem<br>secara<br>otomatis<br>mengkalkulasi<br>ulang<br>_Satisfaciton_<br>_Score_<br>berdasarkan akumulasi laporan user<br>dalam durasi tertentu agar informasi<br>tetap akurat tanpa input manual<br>terus-menerus.|High|On Revi…|



© 2026 Veteran Tech | All right reserved | 7 

## **4. High-Level Architecture** 

## **4.1. High-Level Architecture** 

## **5. Sitemap** 

## **5.1. Landing Page** 

## **5.2. Dashboard User** 

© 2026 Veteran Tech | All right reserved | 8 

## **5.3. Dashboard Admin** 

## **6. Detailed Requirements - Landing Page** 

Landing Page Website merupakan halaman utama yang dapat diakses oleh publik tanpa perlu login. Halaman ini berfungsi sebagai media informasi untuk membantu pengguna menemukan tempat yang tenang di tengah kota serta sebagai pintu masuk ke fitur utama aplikasi. 

|Landing Page Website merupakan halaman utama yang dapat diakses oleh publik tanpa perlu<br>login. Halaman ini berfungsi sebagai media informasi untuk membantu pengguna menemukan<br>tempat yang tenang di tengah kota serta sebagai pintu masuk ke fitur utama aplikasi.|Landing Page Website merupakan halaman utama yang dapat diakses oleh publik tanpa perlu<br>login. Halaman ini berfungsi sebagai media informasi untuk membantu pengguna menemukan<br>tempat yang tenang di tengah kota serta sebagai pintu masuk ke fitur utama aplikasi.|Landing Page Website merupakan halaman utama yang dapat diakses oleh publik tanpa perlu<br>login. Halaman ini berfungsi sebagai media informasi untuk membantu pengguna menemukan<br>tempat yang tenang di tengah kota serta sebagai pintu masuk ke fitur utama aplikasi.|
|---|---|---|
|**Landing Page**|||
|**Scope**|**Content**|**Required Data/Field**|
|Navigation Bar|Menampilkan menu navigasi utama<br>untuk akses ke setiap bagian website|1.<br>Logo Pojok Teduh<br>2.<br>Menu Explore<br>3. Menu About<br>4. Tombol Sign In<br>5. Tombol Sign Up|
|Hero Section|Menampilkan informasi utama<br>platform sebagai pembuka|1.<br>Headline utama (Find a Quiet<br>Spot...)<br>2.<br>Subtext/Deskripsi singkat<br>3. Tombol Get Started<br>4. Tombol Watch Demo<br>5. Gambar ilustrasi/library|
|Problem Section<br>(Urban Reality)|Menampilkan permasalahan yang<br>dihadapi pengguna|1.<br>Judul section<br>2.<br>Deskripsi singkat<br>3. List masalah:<br>a.<br>Noise Pollution<br>b. Endless Crowds|



© 2026 Veteran Tech | All right reserved | 9 

|||c. Unstable<br>Connectivity|
|---|---|---|
|Solutions Section|Menampilkan solusi yang ditawarkan<br>platorm|1.<br>Judul section (Smart<br>Solutions...)<br>2.<br>Deskripsi singkat<br>3. Card ftur:<br>a.<br>Top<br>Recommendation<br>b. Filter<br>c. Satisfaction Score<br>d. Direct Navigation|
|Benefts Section|Menampilkan manfaat bagi pengguna|1.<br>Judul section<br>2.<br>List manfaat:<br>a.<br>Stress Reduction<br>b. Hidden Gems<br>c. Improved<br>Productivity<br>3. Gambar ilustrasi<br>(cafe/workspace)|
|Call To Action<br>(CTA)|Mengajak pengguna untuk<br>menggunakan platorm|1.<br>Headline CTA<br>2.<br>Subtext<br>3. Tombol Sign Up Now<br>4. Tombol Contact Us|
|Footer Website|Menampilkan informasi tambahan<br>terkait platorm|1.<br>Nama platorm (Pojok Teduh)<br>2.<br>Copyright<br>3. Privacy Policy<br>4. Terms of Service<br>5. Contact Us|
|**Important Notes**<br>-<br>Landing page bersifat public (tanpa login)|||



## **Important Notes** 

- Landing page bersifat public (tanpa login) 

© 2026 Veteran Tech | All right reserved | 10 

- Menggunakan konsep Single Page Application (SPA) 

- Fokus utama: user experience yang tenang & minimalis 

- Design menggunakan warna dominan hijau & soft pink (calm aesthetic) 

- Responsive (desktop & mobile) 

## **7. Detailed Requirements - Sign Up Page** 

Sign Up Page Website merupakan halaman yang digunakan oleh pengguna baru untuk membuat akun pada platform Pojok Teduh. Halaman ini berfungsi sebagai pintu masuk awal bagi pengguna agar dapat mendaftar, mengakses sistem, dan menggunakan fitur yang tersedia di dalam platform. 

## **Sign Up Page** 

|agar dapat mendafar, mengakses sistem, dan menggunakan ftur yang tersedia di dalam platorm.|agar dapat mendafar, mengakses sistem, dan menggunakan ftur yang tersedia di dalam platorm.|agar dapat mendafar, mengakses sistem, dan menggunakan ftur yang tersedia di dalam platorm.|
|---|---|---|
|**Sign Up Page**|||
|**Scope**|**Content**|**Required Data/Field**|
|Header /<br>Branding|Menampilkan identitas platorm pada<br>bagian atas form.|1.<br>Logo Pojok Teduh<br>2.<br>Welcome text: “Welcome,<br>Kawan Teduh!”|
|Username Field|Menampilkan feld input untuk nama<br>pengguna.|1.<br>Label: Username<br>2.<br>Input placeholder: Enter your<br>username<br>3. Tipe input: text|
|Email Field|Menampilkan feld input untuk email<br>pengguna.|1.<br>Label: Email<br>2.<br>Input placeholder:<br>user@gmail.com<br>3. Tipe input: email|
|Password Field|Menampilkan feld input untuk kata<br>sandi akun.|1.<br>Label: Password<br>2.<br>Input placeholder: ******<br>3. Tipe input: password|
|Forgot Password<br>Link|Menyediakan navigasi bagi pengguna<br>yang lupa kata sandi.|1.<br>Text link: Forgot password?<br>2.<br>Redirect ke halaman reset<br>password|
|Sign Up Buton|Menyediakan aksi utama untuk|1.<br>Buton text: Sign Up|



© 2026 Veteran Tech | All right reserved | 11 

||proses pendafaran akun baru.|2.|State: default, hover,|
|---|---|---|---|
||||disabled, loading|
|Sign In Redirect|Menyediakan navigasi bagi pengguna|1.|Text: Have an account? Sign|
||yang sudah memiliki akun.||In|
|||2.|Redirect ke halaman login|
|Background /|Menampilkan elemen visual|1.|Background image|
|Visual|pendukung untuk memperkuat|2.|Overlay blur / dim efect|
||branding dan kenyamanan tampilan.|3.|Centered card container|



## **Important Notes** 

- Struktur field saat ini terdiri dari Username, Email, dan Password, mengikuti desain signup page yang sudah dibuat. 

- Link Forgot password pada signup page. 

- Setelah proses signup berhasil, redirect dapat disesuaikan ke Login Page, Email Verification Page, atau langsung ke Dashboard, tergantung kebutuhan sistem. 

## **8. Detailed Requirements - Dashboard User Page** 

Dashboard User merupakan halaman utama yang hanya dapat diakses oleh pengguna yang telah login. Halaman ini berfungsi sebagai pusat eksplorasi lokasi, pencarian tempat, penyimpanan favorit, serta pengelolaan profil dan aktivitas pengguna dalam platform Pojok Teduh. 

|serta pengelolaan profl dan aktivitas pengguna dalam platorm Pojok Teduh.|serta pengelolaan profl dan aktivitas pengguna dalam platorm Pojok Teduh.|serta pengelolaan profl dan aktivitas pengguna dalam platorm Pojok Teduh.|
|---|---|---|
||||
|**Dashboard User Page**|||
|**Scope**|**Content**|**Required Data/Field**|
|Navigation|Menampilkan menu navigasi utama<br>untuk akses ftur dashboard|1.<br>Menu Search<br>2.<br>Menu Bookmark<br>3. Menu Profle|
|Top<br>Recommendatio<br>n|Menampilkan Top Satisfaction Picks<br>berdasarkan tingkat kepuasan dan<br>recently viewed dengan berdasarkan<br>pencarian|1.<br>Card spot insight|



© 2026 Veteran Tech | All right reserved | 12 

|Search Section|Menampilkan ftur pencarian lokasi<br>berdasarkan keyword dan flter|1.<br>Search bar<br>2.<br>Filter fasilitas<br>3. Filter Crowdedness (low dan<br>high)<br>4. List hasil (card spot)|
|---|---|---|
|Card Spot Insight|Menampilkan detail informasi lengkap<br>suatu lokasi|1.<br>Nama tempat<br>2.<br>Foto lokasi<br>3. Deskripsi<br>4. Satisfaction Score<br>a.<br>Nilai rata-rata score<br>b. Menggunakan<br>satisfaction emoji<br>5. Fasilitas<br>6. Jam operasional<br>7.<br>Direct Navigation<br>a.<br>Tombol Go There<br>b. Redirect ke Google<br>Maps<br>c. Koordinat lokasi<br>8. Tombol Bookmark<br>9.<br>Tombol Rate & Review|
|Rating & Review<br>System<br>Satisfaction<br>Score|Menampilkan ulasan pengguna dan<br>penilaian tingkat kepuasan lokasi oleh<br>user|1.<br>Penilaian  (1–5) menurut user<br>2.<br>Input ulasan teks<br>3. List review pengguna|
|Bookmark<br>Section|Menampilkan dafar lokasi favorit<br>yang disimpan oleh pengguna|1.<br>List bookmarked spot<br>2.<br>Card spot preview<br>3. Tombol remove bookmark|
|Profle Section|Menampilkan dan mengelola data<br>akun pengguna|1.<br>Username<br>2.<br>Email<br>3. Foto profl|



© 2026 Veteran Tech | All right reserved | 13 

|||4.|Tombol edit profle|
|---|---|---|---|
|||5.|Setings|
||||a.<br>Change password|
||||b. Account setings|
|History Review|Menampilkan riwayat aktivitas review|1.|List review|
||pengguna|2.|Rating yang diberikan|
|||3.|Lokasi terkait|



## **Important Notes** 

- Tampilan dan interaksi pada dashboard harus mengutamakan kenyamanan visual sesuai konsep “Teduh” (clean, calm, dan tidak cluttered). 

- Integrasi antara fitur Search, Map, dan Detail Spot harus saling terhubung agar memberikan pengalaman eksplorasi yang konsisten. 

## **9. Detailed Requirements - Spot Management Admin Page** 

Spot Management (CRUD) merupakan fitur pada Dashboard Admin yang digunakan untuk mengelola data lokasi/tempat dalam platform Pojok Teduh. Fitur ini berfungsi agar admin dapat menambah, melihat, mengubah, dan menghapus data spot secara terpusat sehingga informasi lokasi pada sistem tetap akurat dan terorganisir. 

|pada sistem tetap akurat dan terorganisir.|pada sistem tetap akurat dan terorganisir.|pada sistem tetap akurat dan terorganisir.|
|---|---|---|
||||
|**Spot Management Page**|||
|**Scope**|**Content**|**Required Data/Field**|
|Spot List Table|Menampilkan dafar seluruh data<br>spot/lokasi yang telah tersimpan di<br>sistem|1.<br>Nama spot<br>2.<br>Kategori spot<br>3. Alamat<br>4. Koordinat lokasi<br>5. Satisfaction Score<br>6. Rating spot<br>7.<br>Aksi Edit<br>8. Aksi Delete|
|Add New Spot|Menampilkan form untuk|1.<br>Nama spot|



© 2026 Veteran Tech | All right reserved | 14 

||menambahkan data spot baru ke<br>sistem|2.<br>Deskripsi spot<br>3. Fasilitas (WiFi, Colokan,<br>Gratis/Berbayar)<br>4. Jam operasional<br>5. Upload foto spot<br>6. Tombol Save|
|---|---|---|
|Edit Spot|Menampilkan form untuk<br>memperbarui data spot yang sudah<br>ada|1.<br>Nama spot<br>2.<br>Kategori spot<br>3. Alamat<br>4. Deskripsi spot<br>5. Fasilitas<br>6. Jam operasional<br>7.<br>Foto spot<br>8. Tombol Update|
|Delete Spot|Menampilkan aksi untuk menghapus<br>data spot dari sistem|1.<br>Tombol Delete<br>2.<br>Modal konfrmasi delete<br>3. Tombol Confrm Delete<br>4. Tombol Cancel|
|**Important Notes**<br>-<br>Struktur dan feld pada ftur Spot Management dapat disesuaikan lebih lanjut berdasarkan<br>kebutuhan data lokasi pada platorm.<br>-<br>Data spot yang dikelola admin harus terhubung dengan ftur Top Recommendation,<br>Search, dan Card Spot Insight agar informasi yang ditampilkan kepada user tetap<br>konsisten.<br>-<br>Proses penghapusan data spot sebaiknya menggunakan konfrmasi tambahan untuk<br>menghindari kesalahan penghapusan data oleh admin.|||



## **Important Notes** 

- Struktur dan field pada fitur Spot Management dapat disesuaikan lebih lanjut berdasarkan kebutuhan data lokasi pada platform. 

- Data spot yang dikelola admin harus terhubung dengan fitur Top Recommendation, Search, dan Card Spot Insight agar informasi yang ditampilkan kepada user tetap konsisten. 

- Proses penghapusan data spot sebaiknya menggunakan konfirmasi tambahan untuk menghindari kesalahan penghapusan data oleh admin. 

## **10. Detailed Requirements - Dashboard Admin Page** 

Dashboard Admin merupakan halaman utama yang hanya dapat diakses oleh admin setelah login. Halaman ini berfungsi sebagai pusat pengelolaan data spot pada platform Pojok Teduh, serta 

© 2026 Veteran Tech | All right reserved | 15 

menampilkan ringkasan kategori spot, aktivitas perubahan data terbaru, dan pengelolaan profil admin. 

|admin.|admin.|admin.|
|---|---|---|
||||
|**Dashboard Admin Page**|||
|**Scope**|**Content**|**Required Data/Field**|
|Navigation Bar /<br>Sidebar|Menampilkan menu navigasi utama<br>untuk akses ftur dashboard admin|1.<br>Menu Home<br>2.<br>Menu Spot Management<br>3. Menu Profle|
|Home Dashboard|Menampilkan ringkasan data utama<br>yang telah dikelola admin|1.<br>Total spot<br>2.<br>Total kategori spot<br>3. Ringkasan jumlah spot per<br>kategori|
|Latest Edit|Menampilkan dafar aktivitas<br>perubahan data spot terbaru|1.<br>Nama spot yang diedit<br>2.<br>Jenis aksi (Add / Edit / Delete)<br>3. Tanggal dan waktu edit|
|Profle|Menampilkan informasi akun admin|1.<br>Nama admin<br>2.<br>Email admin<br>3. Foto profl|
|**Important Notes**<br>-<br>Struktur dan konten pada Dashboard Admin dapat disesuaikan lebih lanjut berdasarkan<br>kebutuhan pengelolaan data spot pada platorm.<br>-<br>Halaman Home Dashboard difokuskan untuk menampilkan ringkasan data utama, seperti<br>jumlah spot berdasarkan kategori dan aktivitas perubahan data terbaru.<br>-<br>Data spot yang dikelola pada ftur Spot Management harus terhubung dengan tampilan<br>spot pada dashboard user agar informasi yang ditampilkan tetap konsisten.|||



## **Important Notes** 

- Struktur dan konten pada Dashboard Admin dapat disesuaikan lebih lanjut berdasarkan kebutuhan pengelolaan data spot pada platform. 

- Halaman Home Dashboard difokuskan untuk menampilkan ringkasan data utama, seperti jumlah spot berdasarkan kategori dan aktivitas perubahan data terbaru. 

- Data spot yang dikelola pada fitur Spot Management harus terhubung dengan tampilan spot pada dashboard user agar informasi yang ditampilkan tetap konsisten. 

## **11. Sprint Roadmap** 

## **11.1. Sprint 1 (5 April – 12 April 2026)** 

## **Review Status** 

© 2026 Veteran Tech | All right reserved | 16 

||||||
|---|---|---|---|---|
|**Approved**|||||
|**No.**|**Activities**|**Detailed Scope**|**Deliverables**|**Role**|
|1.|**PRD**<br>**Finalization**|Penyelarasan scope, MVP,<br>user fow, ekspektasi, dan<br>timeline antar tim.|Dokumen PRD<br>Final (Approve)|PM, Full Team|
|2.|**Account**<br>**Authenticati**<br>**on (Sign In &**<br>**Sign Up)**|Implementasi ftur registrasi<br>dan login user, termasuk<br>validasi input dan integrasi<br>backend|Fitur Sign In & Sign<br>Up berjalan|Frontend,<br>Backend|
|3.|**Landing**<br>**Page**<br>**Developmen**<br>**t**|Implementasi halaman<br>landing page sesuai desain<br>(hero, navigation, CTA)|Landing Page aktif|Frontend|
|4.|**UI/UX**<br>**Design &**<br>**Prototype**|Pembuatan wireframe,<br>design system, hingga<br>high-fdelity dan prototype<br>untuk seluruh ftur (Landing<br>Page, Auth, Dashboard User,<br>Dashboard Admin)|Link Figma (Full<br>Design +<br>Prototype)|UI/UX|
|5.|**Design**<br>**Review &**<br>**Finalization**|Review desain dengan<br>stakeholder dan fnalisasi<br>seluruh tampilan UI/UX<br>sebelum development<br>lanjutan|Design<br>Sign-of<br>(Approved)|Product<br>Manager,<br>UI/UX|
|6.|**Data**<br>**Collection**<br>**Spot**|Pengumpulan data minimal 15<br>lokasi (spot) untuk kebutuhan<br>sistem|Dataset lokasi (15<br>spot)|Product<br>Manager|
|7.|**Repository &**|Setup GitHub, struktur|Repo<br>aktif<br>&|Developers|



© 2026 Veteran Tech | All right reserved | 17 

||**Integration**<br>**Setup**|project, serta integrasi awal<br>frontend dan backend|environment siap||
|---|---|---|---|---|



## **Success Metric** 

1. Seluruh desain UI/UX selesai hingga tahap high-fidelity & prototype 

2. Design telah mendapatkan approval (sign-off) dari tim 

3. Fitur authentication berjalan tanpa error 

4. Landing page berhasil ditampilkan sesuai desain 

5. Minimal 20 data spot terkumpul 

## **11.2. Sprint 2 (11 April – 02 Mei 2026)** 

|**Sprint 2 (11 April – 02 Mei 2026)**|**Sprint 2 (11 April – 02 Mei 2026)**|**Sprint 2 (11 April – 02 Mei 2026)**|**Sprint 2 (11 April – 02 Mei 2026)**|**Sprint 2 (11 April – 02 Mei 2026)**|
|---|---|---|---|---|
||||||
|**Review Status**<br>**Approved**|||||
|**No.**|**Activities**|**Detailed Scope**|**Deliverables**|**Role**|
|1.|**Top**<br>**Recommendati**<br>**on**|Implementasi tampilan<br>card spot insight<br>berdasarkan Top<br>Satisfaction Score dan<br>recently viewed|Card<br>recommendation<br>& recently viewed<br>berjalan|Frontend,<br>Backend|
|2.|**Search & Filter**<br>**Feature**|Implementasi ftur<br>pencarian dan flter<br>berdasarkan suasana dan<br>fasilitas|Search<br>& Filter<br>berjalan|Frontend,<br>Backend|
|3.|**Card Spot**<br>**Insight**|Implementasi tampilan<br>card detail spot (foto,<br>deskripsi, fasilitas, jam<br>operasional)|UI Card Spot|Frontend|
|4.|**Rating &**|Implementasi ftur rating|Sistem<br>Review|Frontend,|



© 2026 Veteran Tech | All right reserved | 18 

||**Review System**|bintang dan ulasan user|berjalan|Backend|
|---|---|---|---|---|
|5.|**Bookmark**<br>**Feature**|Implementasi ftur<br>penyimpanan lokasi<br>favorit oleh user|Bookmark<br>tersimpan|Frontend,<br>Backend|



## **Success Metric** 

1. Search & filter menghasilkan data yang relevan 

2. User dapat memberikan rating & review 

3. User dapat menyimpan dan mengakses bookmark 

## **11.3. Sprint 3 (20 April – 22 Mei 2026)** 

## **Review Status** 

## **Approved** 

|**Sprint 3 (20 April – 22 Mei 2026)**|**Sprint 3 (20 April – 22 Mei 2026)**|**Sprint 3 (20 April – 22 Mei 2026)**|**Sprint 3 (20 April – 22 Mei 2026)**|**Sprint 3 (20 April – 22 Mei 2026)**|
|---|---|---|---|---|
|**Review Status**<br>**Approved**|||||
|**No.**|**Activities**|**Detailed Scope**|**Deliverables**|**Role**|
|1.|**User Profle**<br>**Development**|Implementasi halaman<br>profle user dan<br>pengelolaan data akun|Profle<br>user<br>berjalan|Frontend,<br>Backend|
|2.|**History**<br>**Review**<br>**Feature**|Implementasi ftur riwayat<br>review dan rating user|History<br>review<br>tampil|Frontend,<br>Backend|
|3.|**Admin**<br>**Dashboard**<br>**Development**|Implementasi halaman<br>dashboard admin (home,<br>category summary, latest<br>edit)|Dashboard admin<br>aktif|Frontend|
|4.|**Spot**<br>**Management**<br>**(CRUD)**|Implementasi ftur tambah,<br>edit, hapus data spot oleh<br>admin|CRUD<br>spot<br>berjalan|Frontend,<br>Backend|



© 2026 Veteran Tech | All right reserved | 19 

|5.|**Admin Profle**<br>**Management**|Implementasi ftur<br>pengelolaan akun admin|Profle<br>admin<br>berjalan|Frontend,<br>Backend|
|---|---|---|---|---|



## **Success Metric** 

1. User dapat mengakses dan mengelola profile serta history review 

2. Admin dashboard menampilkan summary kategori & latest edit 

3. Admin dapat melakukan CRUD spot tanpa error 

4. Data spot terintegrasi dengan dashboard user 

