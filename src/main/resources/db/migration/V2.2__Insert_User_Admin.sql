INSERT INTO public.users
(id, username, "password", avatar, created_at, updated_at, email, status, first_name, last_name, phone_no, gender)
VALUES(1, 'admin', '$2a$10$8Foq7O4XmMVH7w6HfkTiUeRCvCrMvyZwdl8BFmXKTck0LRKPaUpfG', NULL, '2023-06-18 09:00:42.680', '2023-06-18 09:00:42.680', 'admin@samsung.com', NULL, NULL, NULL, NULL, NULL);

INSERT INTO public.authorities
(id, username, authority)
VALUES(1, 'admin', 'ADMIN');
