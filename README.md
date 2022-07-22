# Microservices for Online Education

## Description

This is a project for building full-stack online education websites with a backend
and two front-ends, one for customers and the other for site admin/managers. The
backend is built using Spring Cloud and the frontends are written using Vue.

### Backend modules

- **Authentication and authorization:**
This module is built using ```srping security```. Permissions are assigned to roles,
and roles are assigned to users. CRUD operations are implemented for 5 MySQL tables
storing the permission/role/user data and the relations between them. 
Single-sign-on (SSO) is implemented using ```json-web-token``` with user info cached in ```redis```. 

- **Teacher management:**
This module implements the CRUD operations for the MySQL tables related to the teachers
of the online education website.

- **Course management:**
This module implements functions related to online courses. The course category data
are stored in Excel files, which are uploaded to the website, read and processed using
```easy-excel``` and written into MySQL tables. Course categories are assigned to 
individual courses and CRUD operations are implemented for all the course tables.

- **Statistics:**
This module implements the calculations of some statistics related to users and dislays
those statistics using ```ECharts```.

### Frontend modules

- **Customer side:**
  - Front page shows most popular courses and teachers. Latest promotion info is shown in the
  top banner using ```vue-awesome-swiper```. 
  - SSO is implemented using token, which is retrieved from the backend. The token is then stored in cookie and an ```axios``` interceptor checks
  the existence of the token and writes it into the request header. Then user info (e.g., nickname, avatar) is
  requested using the token and stored in cookie. 
  - Teacher info data are displayed on two
  pages: a teacher list page with basic teacher info and avatars, and a teacher details
  page for each teacher in the list. 
  - Course info data are displayed in a course list page
  with basic query and sorting functions and a course details page for each course in the list.
  - Server-side rendering (SSR) is implemented using the ```NUXT``` framework, which is based on ```vue```.

- **Admin side:**
This part is implemented using ```vue-admin-template```, which is based on ```vue``` and ```element-ui```. It provides a web interface
for the CRUD operations on the MySQL tables.

## Built-with

- SpringBoot
- MyBatisPlus
- MySQL
- Redis
- Spring Cloud OpenFeign
- Spring Cloud Nacos
- Spring Cloud Gateway
- Spring Security
- Easy Excel
- Nodejs
- Axios
- Vue
- NUXT
- Vue-admin-template
- Element-ui
- ECharts
- Babel
- Webpack



