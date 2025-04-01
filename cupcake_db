--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.4

-- Started on 2025-04-01 09:07:05 UTC

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 114832)
-- Name: bottoms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bottoms (
    bottom_id integer NOT NULL,
    bottom character varying NOT NULL,
    price integer NOT NULL
);


ALTER TABLE public.bottoms OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 114831)
-- Name: bottoms_bottom_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bottoms_bottom_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bottoms_bottom_id_seq OWNER TO postgres;

--
-- TOC entry 3396 (class 0 OID 0)
-- Dependencies: 218
-- Name: bottoms_bottom_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bottoms_bottom_id_seq OWNED BY public.bottoms.bottom_id;


--
-- TOC entry 224 (class 1259 OID 114857)
-- Name: orderdetails; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderdetails (
    bottom integer NOT NULL,
    topping integer NOT NULL,
    price integer NOT NULL,
    amount integer NOT NULL,
    orderdetails_id integer NOT NULL,
    order_id integer NOT NULL
);


ALTER TABLE public.orderdetails OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 122886)
-- Name: orderdetails_orderdetails_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orderdetails_orderdetails_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orderdetails_orderdetails_id_seq OWNER TO postgres;

--
-- TOC entry 3397 (class 0 OID 0)
-- Dependencies: 225
-- Name: orderdetails_orderdetails_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orderdetails_orderdetails_id_seq OWNED BY public.orderdetails.orderdetails_id;


--
-- TOC entry 223 (class 1259 OID 114850)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    order_id integer NOT NULL,
    customer_id integer,
    date date
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 114849)
-- Name: orders_order_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.orders_order_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.orders_order_id_seq OWNER TO postgres;

--
-- TOC entry 3398 (class 0 OID 0)
-- Dependencies: 222
-- Name: orders_order_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.orders_order_id_seq OWNED BY public.orders.order_id;


--
-- TOC entry 221 (class 1259 OID 114841)
-- Name: toppings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.toppings (
    topping_id integer NOT NULL,
    topping character varying NOT NULL,
    price integer NOT NULL
);


ALTER TABLE public.toppings OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 114840)
-- Name: toppings_topping_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.toppings_topping_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.toppings_topping_id_seq OWNER TO postgres;

--
-- TOC entry 3399 (class 0 OID 0)
-- Dependencies: 220
-- Name: toppings_topping_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.toppings_topping_id_seq OWNED BY public.toppings.topping_id;


--
-- TOC entry 217 (class 1259 OID 114823)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    email character varying NOT NULL,
    user_password character varying NOT NULL,
    balance integer,
    is_admin boolean
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 114822)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 3400 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_user_id_seq OWNED BY public.users.user_id;


--
-- TOC entry 3220 (class 2604 OID 114835)
-- Name: bottoms bottom_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottoms ALTER COLUMN bottom_id SET DEFAULT nextval('public.bottoms_bottom_id_seq'::regclass);


--
-- TOC entry 3223 (class 2604 OID 122887)
-- Name: orderdetails orderdetails_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetails ALTER COLUMN orderdetails_id SET DEFAULT nextval('public.orderdetails_orderdetails_id_seq'::regclass);


--
-- TOC entry 3222 (class 2604 OID 114853)
-- Name: orders order_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders ALTER COLUMN order_id SET DEFAULT nextval('public.orders_order_id_seq'::regclass);


--
-- TOC entry 3221 (class 2604 OID 114844)
-- Name: toppings topping_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.toppings ALTER COLUMN topping_id SET DEFAULT nextval('public.toppings_topping_id_seq'::regclass);


--
-- TOC entry 3219 (class 2604 OID 114826)
-- Name: users user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq'::regclass);


--
-- TOC entry 3384 (class 0 OID 114832)
-- Dependencies: 219
-- Data for Name: bottoms; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.bottoms VALUES (21, 'Chokolade', 5);
INSERT INTO public.bottoms VALUES (22, 'Vanilje', 5);
INSERT INTO public.bottoms VALUES (23, 'Muskatnød', 5);
INSERT INTO public.bottoms VALUES (24, 'Pistacie', 6);
INSERT INTO public.bottoms VALUES (25, 'Mandel', 7);


--
-- TOC entry 3389 (class 0 OID 114857)
-- Dependencies: 224
-- Data for Name: orderdetails; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3388 (class 0 OID 114850)
-- Dependencies: 223
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3386 (class 0 OID 114841)
-- Dependencies: 221
-- Data for Name: toppings; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.toppings VALUES (33, 'Chokolade', 5);
INSERT INTO public.toppings VALUES (34, 'Blåbær', 5);
INSERT INTO public.toppings VALUES (35, 'Hindbær', 5);
INSERT INTO public.toppings VALUES (36, 'Sprød', 6);
INSERT INTO public.toppings VALUES (37, 'Jordbær', 6);
INSERT INTO public.toppings VALUES (38, 'Rom/rosin', 7);
INSERT INTO public.toppings VALUES (39, 'Appelsin', 8);
INSERT INTO public.toppings VALUES (40, 'Citron', 8);
INSERT INTO public.toppings VALUES (41, 'Blåskimmelost', 9);


--
-- TOC entry 3382 (class 0 OID 114823)
-- Dependencies: 217
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users VALUES (1, 'admin', 'admin', 270, true);


--
-- TOC entry 3401 (class 0 OID 0)
-- Dependencies: 218
-- Name: bottoms_bottom_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bottoms_bottom_id_seq', 25, true);


--
-- TOC entry 3402 (class 0 OID 0)
-- Dependencies: 225
-- Name: orderdetails_orderdetails_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orderdetails_orderdetails_id_seq', 5, true);


--
-- TOC entry 3403 (class 0 OID 0)
-- Dependencies: 222
-- Name: orders_order_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.orders_order_id_seq', 2, true);


--
-- TOC entry 3404 (class 0 OID 0)
-- Dependencies: 220
-- Name: toppings_topping_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.toppings_topping_id_seq', 41, true);


--
-- TOC entry 3405 (class 0 OID 0)
-- Dependencies: 216
-- Name: users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_user_id_seq', 1, true);


--
-- TOC entry 3227 (class 2606 OID 114839)
-- Name: bottoms bottoms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bottoms
    ADD CONSTRAINT bottoms_pkey PRIMARY KEY (bottom_id);


--
-- TOC entry 3233 (class 2606 OID 122892)
-- Name: orderdetails orderdetails_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetails
    ADD CONSTRAINT orderdetails_pkey PRIMARY KEY (orderdetails_id);


--
-- TOC entry 3231 (class 2606 OID 114855)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (order_id);


--
-- TOC entry 3229 (class 2606 OID 114848)
-- Name: toppings toppings_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.toppings
    ADD CONSTRAINT toppings_pkey PRIMARY KEY (topping_id);


--
-- TOC entry 3225 (class 2606 OID 114830)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 3235 (class 2606 OID 122893)
-- Name: orderdetails order_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetails
    ADD CONSTRAINT order_id FOREIGN KEY (order_id) REFERENCES public.orders(order_id) NOT VALID;


--
-- TOC entry 3236 (class 2606 OID 114873)
-- Name: orderdetails orderdetails_bottom_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetails
    ADD CONSTRAINT orderdetails_bottom_fkey FOREIGN KEY (bottom) REFERENCES public.bottoms(bottom_id) NOT VALID;


--
-- TOC entry 3237 (class 2606 OID 114878)
-- Name: orderdetails orderdetails_topping_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderdetails
    ADD CONSTRAINT orderdetails_topping_fkey FOREIGN KEY (topping) REFERENCES public.toppings(topping_id) NOT VALID;


--
-- TOC entry 3234 (class 2606 OID 114863)
-- Name: orders orders_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.users(user_id) NOT VALID;


-- Completed on 2025-04-01 09:07:05 UTC

--
-- PostgreSQL database dump complete
--

