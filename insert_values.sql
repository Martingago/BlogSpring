insert into usuarios(username, password, role, name) 
values("admin","$2a$12$7l4YRnXkR8YMQrzOtoJVe.pPQkM8ChhA485fzJLq53COi0/lS.cF.","USER,ADMIN", "admin"), -- pw: admin
("editor", "$2a$12$ygwRDTKqj3EQTtwQsiof3uNqcsFwPCFXwf5YLH1m7myuqV/3W1Yhe", "EDITOR", "editor"), -- pw: editor
("Martin", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Martin Gago"), -- pw: 12345 
("Laura", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Laura Fernández"),
("Carlos", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Carlos Jiménez"),
("Ana", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Ana Martínez"),
("Luis", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Luis Rodríguez"),
("Sofia", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Sofia González"),
("Miguel", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Miguel Sánchez"),
("Isabel", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Isabel Pérez"),
("Diego", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Diego Torres"),
("Elena", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Elena Ruiz"),
("Javier", "$2a$12$ur.02QyguOLHCS9bAv1ziO5qu64v.mf29fcztSYIGNnTOmGtTB.va", "USER", "Javier Castro"),
("Carlitos", "$2a$12$ygwRDTKqj3EQTtwQsiof3uNqcsFwPCFXwf5YLH1m7myuqV/3W1Yhe", "USER, EDITOR", "Carlitos editor"), -- pw: editor
("bamba", "$2a$12$ygwRDTKqj3EQTtwQsiof3uNqcsFwPCFXwf5YLH1m7myuqV/3W1Yhe", "USER, EDITOR", "Bamba editor"); -- pw: editor 

INSERT INTO tags(nombre) VALUES
("Ciencia"),
("Tecnología"),
("JavaScript"),
("Python"),
("Java"),
("C++"),
("Ruby"),
("PHP"),
("HTML"),
("CSS"),
("SQL"),
("React"),
("Angular"),
("Vue.js"),
("Node.js"),
("Django"),
("Flask"),
("Spring"),
("Express"),
("Laravel"),
("Bootstrap"),
("jQuery"),
("TypeScript"),
("Swift"),
("Kotlin");

INSERT INTO posts(titulo, contenido) VALUES
("Introducción a JavaScript", "JavaScript es un lenguaje de programación versátil y esencial para el desarrollo web. En este post, aprenderás los conceptos básicos y cómo empezar a usar JavaScript en tus proyectos."),
("Desarrollando con Python", "Python es conocido por su simplicidad y legibilidad. Este post te guiará a través de los fundamentos de Python y cómo puedes aplicarlo en diversas áreas, desde desarrollo web hasta análisis de datos."),
("Creando aplicaciones con Java", "Java es un lenguaje robusto y ampliamente utilizado en aplicaciones empresariales. Aprende los principios básicos de Java y cómo desarrollar aplicaciones escalables con él."),
("Programación en C++", "C++ es un lenguaje potente que se utiliza en el desarrollo de sistemas operativos, videojuegos y aplicaciones de alto rendimiento. Este post cubre los conceptos esenciales de C++ y ejemplos prácticos."),
("Desarrollando con Ruby on Rails", "Ruby on Rails es un framework popular para el desarrollo de aplicaciones web. Descubre cómo empezar a construir aplicaciones con Ruby on Rails y los beneficios de usar este framework."),
("PHP para desarrollo web", "PHP es un lenguaje de programación utilizado principalmente para el desarrollo web. En este post, exploraremos cómo utilizar PHP para crear sitios web dinámicos y gestionar bases de datos."),
("Fundamentos de HTML y CSS", "HTML y CSS son las piedras angulares del desarrollo web. Aprende cómo estructurar tus páginas web con HTML y estilizar su apariencia con CSS."),
("Introducción a SQL", "SQL es el lenguaje estándar para la gestión de bases de datos. Este post te enseñará cómo utilizar SQL para realizar consultas y gestionar bases de datos de manera eficiente."),
("Desarrollando con React", "React es una biblioteca de JavaScript para construir interfaces de usuario. Aprende cómo empezar con React y crear aplicaciones web interactivas."),
("Angular para aplicaciones dinámicas", "Angular es un framework de JavaScript que facilita la creación de aplicaciones web dinámicas. Descubre cómo empezar a desarrollar con Angular y sus principales características."),
("Guía de Vue.js", "Vue.js es un framework progresivo para construir interfaces de usuario. Este post cubre los fundamentos de Vue.js y cómo puedes usarlo para desarrollar aplicaciones web modernas."),
("Node.js y desarrollo backend", "Node.js permite el desarrollo de aplicaciones del lado del servidor utilizando JavaScript. Aprende los conceptos básicos de Node.js y cómo crear aplicaciones backend eficientes."),
("Desarrollo web con Django", "Django es un framework de Python para el desarrollo web rápido y seguro. Este post te guiará a través de los fundamentos de Django y cómo crear aplicaciones web robustas."),
("Aplicaciones web con Flask", "Flask es un microframework de Python para el desarrollo web. Descubre cómo empezar con Flask y las ventajas de usar este framework ligero para tus proyectos."),
("Introducción a Spring", "Spring es un framework de Java para el desarrollo de aplicaciones empresariales. Aprende cómo usar Spring para crear aplicaciones robustas y escalables."),
("Express para desarrollo backend", "Express es un framework de Node.js para construir aplicaciones web y APIs. En este post, exploraremos cómo empezar a desarrollar con Express."),
("Desarrollando con Laravel", "Laravel es un framework de PHP para el desarrollo de aplicaciones web modernas. Aprende los principios básicos de Laravel y cómo crear aplicaciones web eficientes."),
("Bootstrap para diseño responsive", "Bootstrap es una biblioteca de CSS que facilita el diseño de sitios web responsivos. Descubre cómo usar Bootstrap para crear interfaces de usuario atractivas y adaptables."),
("jQuery para manipulación del DOM", "jQuery es una biblioteca de JavaScript que simplifica la manipulación del DOM. Aprende cómo usar jQuery para añadir interactividad a tus páginas web."),
("Programación con TypeScript", "TypeScript es un superset de JavaScript que añade tipos estáticos. Descubre cómo TypeScript puede mejorar tu desarrollo de JavaScript y ayudarte a construir aplicaciones más robustas."),
("Desarrollando aplicaciones móviles con Swift", "Swift es el lenguaje de programación de Apple para el desarrollo de aplicaciones iOS. Aprende los conceptos básicos de Swift y cómo empezar a desarrollar aplicaciones móviles."),
("Kotlin para desarrollo Android", "Kotlin es un lenguaje moderno que se utiliza para el desarrollo de aplicaciones Android. Este post cubre los fundamentos de Kotlin y cómo empezar a desarrollar aplicaciones móviles con él.");

INSERT INTO posts_tags(post_id, tag_id) VALUES
(1, 3),   -- Introducción a JavaScript (JavaScript)
(1, 2),   -- Introducción a JavaScript (Tecnología)
(2, 4),   -- Desarrollando con Python (Python)
(2, 2),   -- Desarrollando con Python (Tecnología)
(3, 5),   -- Creando aplicaciones con Java (Java)
(3, 2),   -- Creando aplicaciones con Java (Tecnología)
(4, 6),   -- Programación en C++ (C++)
(4, 1),   -- Programación en C++ (Ciencia)
(5, 7),   -- Desarrollando con Ruby on Rails (Ruby)
(5, 2),   -- Desarrollando con Ruby on Rails (Tecnología)
(6, 8),   -- PHP para desarrollo web (PHP)
(6, 2),   -- PHP para desarrollo web (Tecnología)
(7, 9),   -- Fundamentos de HTML y CSS (HTML)
(7, 10),  -- Fundamentos de HTML y CSS (CSS)
(7, 2),   -- Fundamentos de HTML y CSS (Tecnología)
(8, 11),  -- Introducción a SQL (SQL)
(8, 2),   -- Introducción a SQL (Tecnología)
(9, 12),  -- Desarrollando con React (React)
(9, 2),   -- Desarrollando con React (Tecnología)
(10, 13), -- Angular para aplicaciones dinámicas (Angular)
(10, 2),  -- Angular para aplicaciones dinámicas (Tecnología)
(11, 14), -- Guía de Vue.js (Vue.js)
(11, 2),  -- Guía de Vue.js (Tecnología)
(12, 15), -- Node.js y desarrollo backend (Node.js)
(12, 2),  -- Node.js y desarrollo backend (Tecnología)
(13, 16), -- Desarrollo web con Django (Django)
(13, 1),  -- Desarrollo web con Django (Ciencia)
(14, 17), -- Aplicaciones web con Flask (Flask)
(14, 1),  -- Aplicaciones web con Flask (Ciencia)
(15, 18), -- Introducción a Spring (Spring)
(15, 1),  -- Introducción a Spring (Ciencia)
(16, 19), -- Express para desarrollo backend (Express)
(16, 2),  -- Express para desarrollo backend (Tecnología)
(17, 20), -- Desarrollando con Laravel (Laravel)
(17, 2),  -- Desarrollando con Laravel (Tecnología)
(18, 21), -- Bootstrap para diseño responsive (Bootstrap)
(18, 2),  -- Bootstrap para diseño responsive (Tecnología)
(19, 22), -- jQuery para manipulación del DOM (jQuery)
(19, 2),  -- jQuery para manipulación del DOM (Tecnología)
(20, 23), -- Programación con TypeScript (TypeScript)
(20, 2),  -- Programación con TypeScript (Tecnología)
(21, 24), -- Desarrollando aplicaciones móviles con Swift (Swift)
(21, 2),  -- Desarrollando aplicaciones móviles con Swift (Tecnología)
(22, 25), -- Kotlin para desarrollo Android (Kotlin)
(22, 2);  -- Kotlin para desarrollo Android (Tecnología)


INSERT INTO post_details(fecha_creacion, post_id, created_by) VALUES
('2024-01-10', 1, 1),   -- Introducción a JavaScript
('2024-02-14', 2, 2),   -- Desarrollando con Python
('2024-03-20', 3, 14),  -- Creando aplicaciones con Java
('2024-04-05', 4, 15),  -- Programación en C++
('2024-05-12', 5, 1),   -- Desarrollando con Ruby on Rails
('2024-06-22', 6, 2),   -- PHP para desarrollo web
('2024-07-08', 7, 14),  -- Fundamentos de HTML y CSS
('2024-08-15', 8, 15),  -- Introducción a SQL
('2024-09-30', 9, 1),   -- Desarrollando con React
('2024-10-21', 10, 2),  -- Angular para aplicaciones dinámicas
('2024-11-05', 11, 14), -- Guía de Vue.js
('2024-12-18', 12, 15), -- Node.js y desarrollo backend
('2024-01-11', 13, 1),  -- Desarrollo web con Django
('2024-02-23', 14, 2),  -- Aplicaciones web con Flask
('2024-03-17', 15, 14), -- Introducción a Spring
('2024-04-29', 16, 15), -- Express para desarrollo backend
('2024-05-25', 17, 1),  -- Desarrollando con Laravel
('2024-06-16', 18, 2),  -- Bootstrap para diseño responsive
('2024-07-19', 19, 14), -- jQuery para manipulación del DOM
('2024-08-12', 20, 15), -- Programación con TypeScript
('2024-09-02', 21, 1),  -- Desarrollando aplicaciones móviles con Swift
('2024-10-27', 22, 2);  -- Kotlin para desarrollo Android









