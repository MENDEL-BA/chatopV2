PGDMP     0            
        z         	   clinic_v2    14.2    14.2 c    ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    24576 	   clinic_v2    DATABASE     ^   CREATE DATABASE clinic_v2 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
    DROP DATABASE clinic_v2;
                postgres    false            ?            1259    34065    chambre    TABLE     2  CREATE TABLE public.chambre (
    id bigint NOT NULL,
    capacite_chambre integer NOT NULL,
    localisation_chambre character varying(255) NOT NULL,
    prix_chambre character varying(255) NOT NULL,
    type_chambre character varying(255) NOT NULL,
    linked_meta bigint NOT NULL,
    lits_id bigint
);
    DROP TABLE public.chambre;
       public         heap    postgres    false            ?            1259    33020    confirmation_token    TABLE     '  CREATE TABLE public.confirmation_token (
    id bigint NOT NULL,
    confirmed_at timestamp without time zone,
    created_at timestamp without time zone NOT NULL,
    expires_at timestamp without time zone NOT NULL,
    token character varying(255) NOT NULL,
    app_user_id bigint NOT NULL
);
 &   DROP TABLE public.confirmation_token;
       public         heap    postgres    false            ?            1259    33010    confirmation_token_sequence    SEQUENCE     ?   CREATE SEQUENCE public.confirmation_token_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public.confirmation_token_sequence;
       public          postgres    false            ?            1259    34072    consultation    TABLE     P  CREATE TABLE public.consultation (
    id bigint NOT NULL,
    date_consultation date NOT NULL,
    diagnostic_consultation character varying(255) NOT NULL,
    prix_consultation character varying(255),
    type_consultation character varying(255) NOT NULL,
    linked_meta bigint NOT NULL,
    patient_id bigint,
    user_id bigint
);
     DROP TABLE public.consultation;
       public         heap    postgres    false            ?            1259    65908    dossier_medical    TABLE     ?   CREATE TABLE public.dossier_medical (
    id bigint NOT NULL,
    date_creation_dossier date NOT NULL,
    libelle_dos character varying(255),
    linked_meta bigint NOT NULL,
    patient_id bigint,
    medecin_id bigint
);
 #   DROP TABLE public.dossier_medical;
       public         heap    postgres    false            ?            1259    34079    facture    TABLE     ?   CREATE TABLE public.facture (
    id bigint NOT NULL,
    date_facturation date NOT NULL,
    date_payement date NOT NULL,
    est_reglee boolean,
    montant_net integer,
    linked_meta bigint NOT NULL,
    patient_id bigint
);
    DROP TABLE public.facture;
       public         heap    postgres    false            ?            1259    34084    hospitalisation    TABLE     ?  CREATE TABLE public.hospitalisation (
    id bigint NOT NULL,
    cause_deces character varying(255),
    date_admission date NOT NULL,
    date_deces character varying(255),
    date_sortie date,
    date_transfert character varying(255),
    dossier_medical character varying(255),
    infos_accompagnat character varying(255) NOT NULL,
    motif_admission character varying(255) NOT NULL,
    motif_sortie character varying(255),
    linked_meta bigint NOT NULL,
    lits_id bigint,
    user_id bigint
);
 #   DROP TABLE public.hospitalisation;
       public         heap    postgres    false            ?            1259    49384    lit    TABLE     ?   CREATE TABLE public.lit (
    id bigint NOT NULL,
    etat boolean,
    numero character varying(255),
    linked_meta bigint NOT NULL,
    patient_id bigint
);
    DROP TABLE public.lit;
       public         heap    postgres    false            ?            1259    49506    menu    TABLE     ?   CREATE TABLE public.menu (
    id bigint NOT NULL,
    is_authorized boolean NOT NULL,
    titre_menu character varying(255) NOT NULL,
    linked_meta bigint NOT NULL,
    user_id bigint
);
    DROP TABLE public.menu;
       public         heap    postgres    false            ?            1259    49505    menu_id_seq    SEQUENCE     t   CREATE SEQUENCE public.menu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.menu_id_seq;
       public          postgres    false    228            ?           0    0    menu_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.menu_id_seq OWNED BY public.menu.id;
          public          postgres    false    227            ?            1259    34097    meta    TABLE       CREATE TABLE public.meta (
    id bigint NOT NULL,
    created_by bigint,
    created_at timestamp without time zone NOT NULL,
    external_id character varying(255) NOT NULL,
    last_modified timestamp without time zone,
    resource_type character varying(55) NOT NULL
);
    DROP TABLE public.meta;
       public         heap    postgres    false            ?            1259    34096    meta_id_seq    SEQUENCE     t   CREATE SEQUENCE public.meta_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.meta_id_seq;
       public          postgres    false    218            ?           0    0    meta_id_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE public.meta_id_seq OWNED BY public.meta.id;
          public          postgres    false    217            ?            1259    57697 
   ordonnance    TABLE     ?   CREATE TABLE public.ordonnance (
    id bigint NOT NULL,
    date_prescription date NOT NULL,
    medicaments_prescrits character varying(255) NOT NULL,
    medecin_id bigint,
    patient_id bigint,
    linked_meta bigint NOT NULL
);
    DROP TABLE public.ordonnance;
       public         heap    postgres    false            ?            1259    34103    patient    TABLE     .  CREATE TABLE public.patient (
    id bigint NOT NULL,
    assurance_medicale character varying(255),
    code_assurance character varying(255),
    email_patient character varying(255),
    nom_and_prenom_ice character varying(255) NOT NULL,
    nom_patient character varying(255) NOT NULL,
    numero_telephone_ice character varying(255) NOT NULL,
    numero_telephone_patient character varying(255) NOT NULL,
    prenom_patient character varying(255) NOT NULL,
    situation_matrimoniale character varying(255) NOT NULL,
    linked_meta bigint NOT NULL
);
    DROP TABLE public.patient;
       public         heap    postgres    false            ?            1259    34110    rendez_vous    TABLE     ?   CREATE TABLE public.rendez_vous (
    id bigint NOT NULL,
    date_rdv date NOT NULL,
    heure_rdv character varying(255),
    linked_meta bigint NOT NULL,
    patient_id bigint,
    user_id bigint
);
    DROP TABLE public.rendez_vous;
       public         heap    postgres    false            ?            1259    34116    roles    TABLE     W   CREATE TABLE public.roles (
    id integer NOT NULL,
    name character varying(20)
);
    DROP TABLE public.roles;
       public         heap    postgres    false            ?            1259    34115    roles_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.roles_id_seq;
       public          postgres    false    222            ?           0    0    roles_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;
          public          postgres    false    221            ?            1259    34063    sequence_generator    SEQUENCE     |   CREATE SEQUENCE public.sequence_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.sequence_generator;
       public          postgres    false            ?            1259    65890    specialite_medecin    TABLE     ?   CREATE TABLE public.specialite_medecin (
    id bigint NOT NULL,
    nom_specialite character varying(20),
    linked_meta bigint NOT NULL
);
 &   DROP TABLE public.specialite_medecin;
       public         heap    postgres    false            ?            1259    65889    specialite_medecin_id_seq    SEQUENCE     ?   CREATE SEQUENCE public.specialite_medecin_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public.specialite_medecin_id_seq;
       public          postgres    false    231            ?           0    0    specialite_medecin_id_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public.specialite_medecin_id_seq OWNED BY public.specialite_medecin.id;
          public          postgres    false    230            ?            1259    34064    student_sequence    SEQUENCE     y   CREATE SEQUENCE public.student_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.student_sequence;
       public          postgres    false            ?            1259    34122 
   user_roles    TABLE     ^   CREATE TABLE public.user_roles (
    user_id bigint NOT NULL,
    role_id integer NOT NULL
);
    DROP TABLE public.user_roles;
       public         heap    postgres    false            ?            1259    34128    users    TABLE     ?  CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(50),
    password character varying(120),
    username character varying(20),
    linked_meta bigint NOT NULL,
    first_name character varying(120),
    last_name character varying(120),
    numero_telephone character varying(120),
    specialite_medecin_id bigint,
    location character varying(255)
);
    DROP TABLE public.users;
       public         heap    postgres    false            ?            1259    34127    users_id_seq    SEQUENCE     u   CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.users_id_seq;
       public          postgres    false    225            ?           0    0    users_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
          public          postgres    false    224            ?
           2604    49509    menu id    DEFAULT     b   ALTER TABLE ONLY public.menu ALTER COLUMN id SET DEFAULT nextval('public.menu_id_seq'::regclass);
 6   ALTER TABLE public.menu ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    227    228    228            ?
           2604    34100    meta id    DEFAULT     b   ALTER TABLE ONLY public.meta ALTER COLUMN id SET DEFAULT nextval('public.meta_id_seq'::regclass);
 6   ALTER TABLE public.meta ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    217    218            ?
           2604    34119    roles id    DEFAULT     d   ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);
 7   ALTER TABLE public.roles ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    221    222    222            ?
           2604    65893    specialite_medecin id    DEFAULT     ~   ALTER TABLE ONLY public.specialite_medecin ALTER COLUMN id SET DEFAULT nextval('public.specialite_medecin_id_seq'::regclass);
 D   ALTER TABLE public.specialite_medecin ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    230    231    231            ?
           2604    34131    users id    DEFAULT     d   ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);
 7   ALTER TABLE public.users ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    225    224    225            ?
           2606    34071    chambre chambre_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.chambre
    ADD CONSTRAINT chambre_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.chambre DROP CONSTRAINT chambre_pkey;
       public            postgres    false    213            ?
           2606    33024 *   confirmation_token confirmation_token_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.confirmation_token
    ADD CONSTRAINT confirmation_token_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.confirmation_token DROP CONSTRAINT confirmation_token_pkey;
       public            postgres    false    210            ?
           2606    34078    consultation consultation_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.consultation
    ADD CONSTRAINT consultation_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.consultation DROP CONSTRAINT consultation_pkey;
       public            postgres    false    214            ?
           2606    65912 $   dossier_medical dossier_medical_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.dossier_medical
    ADD CONSTRAINT dossier_medical_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.dossier_medical DROP CONSTRAINT dossier_medical_pkey;
       public            postgres    false    232            ?
           2606    34083    facture facture_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.facture
    ADD CONSTRAINT facture_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.facture DROP CONSTRAINT facture_pkey;
       public            postgres    false    215            ?
           2606    34090 $   hospitalisation hospitalisation_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.hospitalisation
    ADD CONSTRAINT hospitalisation_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.hospitalisation DROP CONSTRAINT hospitalisation_pkey;
       public            postgres    false    216            ?
           2606    49388    lit lit_pkey 
   CONSTRAINT     J   ALTER TABLE ONLY public.lit
    ADD CONSTRAINT lit_pkey PRIMARY KEY (id);
 6   ALTER TABLE ONLY public.lit DROP CONSTRAINT lit_pkey;
       public            postgres    false    226            ?
           2606    49511    menu menu_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.menu
    ADD CONSTRAINT menu_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.menu DROP CONSTRAINT menu_pkey;
       public            postgres    false    228            ?
           2606    34102    meta meta_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.meta
    ADD CONSTRAINT meta_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.meta DROP CONSTRAINT meta_pkey;
       public            postgres    false    218            ?
           2606    57701    ordonnance ordonnance_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.ordonnance
    ADD CONSTRAINT ordonnance_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.ordonnance DROP CONSTRAINT ordonnance_pkey;
       public            postgres    false    229            ?
           2606    34109    patient patient_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.patient DROP CONSTRAINT patient_pkey;
       public            postgres    false    219            ?
           2606    34114    rendez_vous rendez_vous_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.rendez_vous
    ADD CONSTRAINT rendez_vous_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.rendez_vous DROP CONSTRAINT rendez_vous_pkey;
       public            postgres    false    220            ?
           2606    34121    roles roles_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public            postgres    false    222            ?
           2606    65895 *   specialite_medecin specialite_medecin_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.specialite_medecin
    ADD CONSTRAINT specialite_medecin_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.specialite_medecin DROP CONSTRAINT specialite_medecin_pkey;
       public            postgres    false    231            ?
           2606    34155 !   users uk6dotkott2kjsp8vw4d0m25fb7 
   CONSTRAINT     ]   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7;
       public            postgres    false    225            ?
           2606    34147 $   patient uk_1fh92ntybvr2704s3g610qxmo 
   CONSTRAINT     s   ALTER TABLE ONLY public.patient
    ADD CONSTRAINT uk_1fh92ntybvr2704s3g610qxmo UNIQUE (numero_telephone_patient);
 N   ALTER TABLE ONLY public.patient DROP CONSTRAINT uk_1fh92ntybvr2704s3g610qxmo;
       public            postgres    false    219            ?
           2606    65897 /   specialite_medecin uk_2ytd96jnrj1v9h1erod49e9ac 
   CONSTRAINT     q   ALTER TABLE ONLY public.specialite_medecin
    ADD CONSTRAINT uk_2ytd96jnrj1v9h1erod49e9ac UNIQUE (linked_meta);
 Y   ALTER TABLE ONLY public.specialite_medecin DROP CONSTRAINT uk_2ytd96jnrj1v9h1erod49e9ac;
       public            postgres    false    231            ?
           2606    49390     lit uk_4ymdqdkow3reiv4ucn7hmxxcg 
   CONSTRAINT     b   ALTER TABLE ONLY public.lit
    ADD CONSTRAINT uk_4ymdqdkow3reiv4ucn7hmxxcg UNIQUE (linked_meta);
 J   ALTER TABLE ONLY public.lit DROP CONSTRAINT uk_4ymdqdkow3reiv4ucn7hmxxcg;
       public            postgres    false    226            ?
           2606    34151 "   users uk_6xnvkhagmphqebl0916957r56 
   CONSTRAINT     d   ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6xnvkhagmphqebl0916957r56 UNIQUE (linked_meta);
 L   ALTER TABLE ONLY public.users DROP CONSTRAINT uk_6xnvkhagmphqebl0916957r56;
       public            postgres    false    225            ?
           2606    49313 ,   hospitalisation uk_9gnwg0q8kf07xbauv26lsqaf1 
   CONSTRAINT     n   ALTER TABLE ONLY public.hospitalisation
    ADD CONSTRAINT uk_9gnwg0q8kf07xbauv26lsqaf1 UNIQUE (linked_meta);
 V   ALTER TABLE ONLY public.hospitalisation DROP CONSTRAINT uk_9gnwg0q8kf07xbauv26lsqaf1;
       public            postgres    false    216            ?
           2606    34139 $   facture uk_b6mb8xql4f02ykhy45nurc6e6 
   CONSTRAINT     f   ALTER TABLE ONLY public.facture
    ADD CONSTRAINT uk_b6mb8xql4f02ykhy45nurc6e6 UNIQUE (linked_meta);
 N   ALTER TABLE ONLY public.facture DROP CONSTRAINT uk_b6mb8xql4f02ykhy45nurc6e6;
       public            postgres    false    215            ?
           2606    57713 '   ordonnance uk_e11o3xryhijhvvwqcfpsa7iai 
   CONSTRAINT     i   ALTER TABLE ONLY public.ordonnance
    ADD CONSTRAINT uk_e11o3xryhijhvvwqcfpsa7iai UNIQUE (linked_meta);
 Q   ALTER TABLE ONLY public.ordonnance DROP CONSTRAINT uk_e11o3xryhijhvvwqcfpsa7iai;
       public            postgres    false    229            ?
           2606    34135 $   chambre uk_gg79ss5jaeubc44pg8n4wqld0 
   CONSTRAINT     f   ALTER TABLE ONLY public.chambre
    ADD CONSTRAINT uk_gg79ss5jaeubc44pg8n4wqld0 UNIQUE (linked_meta);
 N   ALTER TABLE ONLY public.chambre DROP CONSTRAINT uk_gg79ss5jaeubc44pg8n4wqld0;
       public            postgres    false    213            ?
           2606    34137 )   consultation uk_j07dg0pj453unbes1dpi9qvb0 
   CONSTRAINT     k   ALTER TABLE ONLY public.consultation
    ADD CONSTRAINT uk_j07dg0pj453unbes1dpi9qvb0 UNIQUE (linked_meta);
 S   ALTER TABLE ONLY public.consultation DROP CONSTRAINT uk_j07dg0pj453unbes1dpi9qvb0;
       public            postgres    false    214            ?
           2606    34149 (   rendez_vous uk_kx2wuofb5v41j86s7x2xie433 
   CONSTRAINT     j   ALTER TABLE ONLY public.rendez_vous
    ADD CONSTRAINT uk_kx2wuofb5v41j86s7x2xie433 UNIQUE (linked_meta);
 R   ALTER TABLE ONLY public.rendez_vous DROP CONSTRAINT uk_kx2wuofb5v41j86s7x2xie433;
       public            postgres    false    220            ?
           2606    34145 $   patient uk_lppbx89ar54i3pwau9sgf43co 
   CONSTRAINT     o   ALTER TABLE ONLY public.patient
    ADD CONSTRAINT uk_lppbx89ar54i3pwau9sgf43co UNIQUE (numero_telephone_ice);
 N   ALTER TABLE ONLY public.patient DROP CONSTRAINT uk_lppbx89ar54i3pwau9sgf43co;
       public            postgres    false    219            ?
           2606    49513 !   menu uk_navkq0deo3u3e5yiu5hi1y7ai 
   CONSTRAINT     c   ALTER TABLE ONLY public.menu
    ADD CONSTRAINT uk_navkq0deo3u3e5yiu5hi1y7ai UNIQUE (linked_meta);
 K   ALTER TABLE ONLY public.menu DROP CONSTRAINT uk_navkq0deo3u3e5yiu5hi1y7ai;
       public            postgres    false    228            ?
           2606    34143 #   patient uk_q6hfgtdbo96r5p650jsjqlqs 
   CONSTRAINT     e   ALTER TABLE ONLY public.patient
    ADD CONSTRAINT uk_q6hfgtdbo96r5p650jsjqlqs UNIQUE (linked_meta);
 M   ALTER TABLE ONLY public.patient DROP CONSTRAINT uk_q6hfgtdbo96r5p650jsjqlqs;
       public            postgres    false    219            ?
           2606    65914 ,   dossier_medical uk_rwvqufs82g3jvxjsfunk4e0bs 
   CONSTRAINT     n   ALTER TABLE ONLY public.dossier_medical
    ADD CONSTRAINT uk_rwvqufs82g3jvxjsfunk4e0bs UNIQUE (linked_meta);
 V   ALTER TABLE ONLY public.dossier_medical DROP CONSTRAINT uk_rwvqufs82g3jvxjsfunk4e0bs;
       public            postgres    false    232            ?
           2606    34153 !   users ukr43af9ap4edm43mmtq01oddj6 
   CONSTRAINT     `   ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT ukr43af9ap4edm43mmtq01oddj6;
       public            postgres    false    225            ?
           2606    34126    user_roles user_roles_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id);
 D   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT user_roles_pkey;
       public            postgres    false    223    223            ?
           2606    34133    users users_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.users DROP CONSTRAINT users_pkey;
       public            postgres    false    225            ?
           2606    49396 +   hospitalisation fk1vtiutt09bnvhbr6kwhatr5h6 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.hospitalisation
    ADD CONSTRAINT fk1vtiutt09bnvhbr6kwhatr5h6 FOREIGN KEY (lits_id) REFERENCES public.lit(id);
 U   ALTER TABLE ONLY public.hospitalisation DROP CONSTRAINT fk1vtiutt09bnvhbr6kwhatr5h6;
       public          postgres    false    216    226    3548            ?
           2606    34216 '   rendez_vous fk35ayulwe26jii3vq14v6sokp3 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.rendez_vous
    ADD CONSTRAINT fk35ayulwe26jii3vq14v6sokp3 FOREIGN KEY (patient_id) REFERENCES public.patient(id);
 Q   ALTER TABLE ONLY public.rendez_vous DROP CONSTRAINT fk35ayulwe26jii3vq14v6sokp3;
       public          postgres    false    220    3524    219            ?
           2606    49316 +   hospitalisation fk42tg5jvrn12gl0ve4hf3u1wpl 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.hospitalisation
    ADD CONSTRAINT fk42tg5jvrn12gl0ve4hf3u1wpl FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 U   ALTER TABLE ONLY public.hospitalisation DROP CONSTRAINT fk42tg5jvrn12gl0ve4hf3u1wpl;
       public          postgres    false    218    3522    216            ?
           2606    49391 #   chambre fk4bumpdbe78vxjbh0bm8dw6plt 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.chambre
    ADD CONSTRAINT fk4bumpdbe78vxjbh0bm8dw6plt FOREIGN KEY (lits_id) REFERENCES public.lit(id);
 M   ALTER TABLE ONLY public.chambre DROP CONSTRAINT fk4bumpdbe78vxjbh0bm8dw6plt;
       public          postgres    false    3548    213    226            ?
           2606    34236 !   users fk78avntphdpuxh5mlrm91srov1 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk78avntphdpuxh5mlrm91srov1 FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT fk78avntphdpuxh5mlrm91srov1;
       public          postgres    false    225    218    3522            ?
           2606    34171 (   consultation fk7us6be9pm4xnpnf70gx1rt39k 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.consultation
    ADD CONSTRAINT fk7us6be9pm4xnpnf70gx1rt39k FOREIGN KEY (patient_id) REFERENCES public.patient(id);
 R   ALTER TABLE ONLY public.consultation DROP CONSTRAINT fk7us6be9pm4xnpnf70gx1rt39k;
       public          postgres    false    219    214    3524                       2606    49401    lit fk8bdvyqh0jk44iw9ahl7dygrq1 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.lit
    ADD CONSTRAINT fk8bdvyqh0jk44iw9ahl7dygrq1 FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 I   ALTER TABLE ONLY public.lit DROP CONSTRAINT fk8bdvyqh0jk44iw9ahl7dygrq1;
       public          postgres    false    218    226    3522            ?
           2606    34166 (   consultation fk9cpegqyxgausftolcvu05j1l3 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.consultation
    ADD CONSTRAINT fk9cpegqyxgausftolcvu05j1l3 FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 R   ALTER TABLE ONLY public.consultation DROP CONSTRAINT fk9cpegqyxgausftolcvu05j1l3;
       public          postgres    false    214    3522    218                       2606    57702 &   ordonnance fk9vqolyxgth6yd7d4uihqkbh1y 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.ordonnance
    ADD CONSTRAINT fk9vqolyxgth6yd7d4uihqkbh1y FOREIGN KEY (medecin_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.ordonnance DROP CONSTRAINT fk9vqolyxgth6yd7d4uihqkbh1y;
       public          postgres    false    229    3546    225            ?
           2606    34176 '   consultation fkabhfs4kpxnm50bwdwcx2w6cc 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.consultation
    ADD CONSTRAINT fkabhfs4kpxnm50bwdwcx2w6cc FOREIGN KEY (user_id) REFERENCES public.users(id);
 Q   ALTER TABLE ONLY public.consultation DROP CONSTRAINT fkabhfs4kpxnm50bwdwcx2w6cc;
       public          postgres    false    225    3546    214                       2606    57714 &   ordonnance fkbigugg30khw9hrr9d8ams1hq5 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.ordonnance
    ADD CONSTRAINT fkbigugg30khw9hrr9d8ams1hq5 FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 P   ALTER TABLE ONLY public.ordonnance DROP CONSTRAINT fkbigugg30khw9hrr9d8ams1hq5;
       public          postgres    false    218    3522    229                       2606    49514     menu fkcsddlxvld5wq84pkr6x61ytam 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.menu
    ADD CONSTRAINT fkcsddlxvld5wq84pkr6x61ytam FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 J   ALTER TABLE ONLY public.menu DROP CONSTRAINT fkcsddlxvld5wq84pkr6x61ytam;
       public          postgres    false    218    3522    228                       2606    65898 .   specialite_medecin fkecih4bka9tjm7ucw66170xwax 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.specialite_medecin
    ADD CONSTRAINT fkecih4bka9tjm7ucw66170xwax FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 X   ALTER TABLE ONLY public.specialite_medecin DROP CONSTRAINT fkecih4bka9tjm7ucw66170xwax;
       public          postgres    false    218    3522    231            ?
           2606    34186 #   facture fkfsdi28aa87t124c43hwtvaly5 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.facture
    ADD CONSTRAINT fkfsdi28aa87t124c43hwtvaly5 FOREIGN KEY (patient_id) REFERENCES public.patient(id);
 M   ALTER TABLE ONLY public.facture DROP CONSTRAINT fkfsdi28aa87t124c43hwtvaly5;
       public          postgres    false    215    3524    219                       2606    65925 +   dossier_medical fkg8fvvttxe4paptqewwy5nopsh 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.dossier_medical
    ADD CONSTRAINT fkg8fvvttxe4paptqewwy5nopsh FOREIGN KEY (medecin_id) REFERENCES public.users(id);
 U   ALTER TABLE ONLY public.dossier_medical DROP CONSTRAINT fkg8fvvttxe4paptqewwy5nopsh;
       public          postgres    false    232    3546    225            ?
           2606    34211 '   rendez_vous fkg93lww92x4wwmwxm8bfhi9tss 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.rendez_vous
    ADD CONSTRAINT fkg93lww92x4wwmwxm8bfhi9tss FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 Q   ALTER TABLE ONLY public.rendez_vous DROP CONSTRAINT fkg93lww92x4wwmwxm8bfhi9tss;
       public          postgres    false    3522    218    220            ?
           2606    34226 &   user_roles fkh8ciramu9cc9q3qcqiv4ue8a6 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6;
       public          postgres    false    222    223    3536            
           2606    65920 +   dossier_medical fkhacw8x7861tc1hne9q1tbyi9g 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.dossier_medical
    ADD CONSTRAINT fkhacw8x7861tc1hne9q1tbyi9g FOREIGN KEY (patient_id) REFERENCES public.patient(id);
 U   ALTER TABLE ONLY public.dossier_medical DROP CONSTRAINT fkhacw8x7861tc1hne9q1tbyi9g;
       public          postgres    false    232    219    3524            ?
           2606    34231 &   user_roles fkhfh9dx7w3ubf1co1vdev94g3f 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id);
 P   ALTER TABLE ONLY public.user_roles DROP CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f;
       public          postgres    false    223    225    3546            ?
           2606    49485 +   hospitalisation fkhl6388p5s7iykxgb9ujeu2e28 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.hospitalisation
    ADD CONSTRAINT fkhl6388p5s7iykxgb9ujeu2e28 FOREIGN KEY (user_id) REFERENCES public.users(id);
 U   ALTER TABLE ONLY public.hospitalisation DROP CONSTRAINT fkhl6388p5s7iykxgb9ujeu2e28;
       public          postgres    false    225    216    3546            ?
           2606    34156 #   chambre fkj3aqb9kudhigxh1tamsc3kehd 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.chambre
    ADD CONSTRAINT fkj3aqb9kudhigxh1tamsc3kehd FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 M   ALTER TABLE ONLY public.chambre DROP CONSTRAINT fkj3aqb9kudhigxh1tamsc3kehd;
       public          postgres    false    213    3522    218                       2606    49406    lit fklcpn11u3rgpkpsevrvbjgblny 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.lit
    ADD CONSTRAINT fklcpn11u3rgpkpsevrvbjgblny FOREIGN KEY (patient_id) REFERENCES public.patient(id);
 I   ALTER TABLE ONLY public.lit DROP CONSTRAINT fklcpn11u3rgpkpsevrvbjgblny;
       public          postgres    false    219    226    3524                       2606    49519     menu fkma5mi2djt4vfyg571d3w2hu0u 
   FK CONSTRAINT        ALTER TABLE ONLY public.menu
    ADD CONSTRAINT fkma5mi2djt4vfyg571d3w2hu0u FOREIGN KEY (user_id) REFERENCES public.users(id);
 J   ALTER TABLE ONLY public.menu DROP CONSTRAINT fkma5mi2djt4vfyg571d3w2hu0u;
       public          postgres    false    225    3546    228                       2606    57707 &   ordonnance fkpyrgsls35n0ttep37dwdyhv85 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.ordonnance
    ADD CONSTRAINT fkpyrgsls35n0ttep37dwdyhv85 FOREIGN KEY (patient_id) REFERENCES public.patient(id);
 P   ALTER TABLE ONLY public.ordonnance DROP CONSTRAINT fkpyrgsls35n0ttep37dwdyhv85;
       public          postgres    false    219    229    3524            ?
           2606    34206 #   patient fkq56ch51cr7048bwkru7vvp925 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fkq56ch51cr7048bwkru7vvp925 FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 M   ALTER TABLE ONLY public.patient DROP CONSTRAINT fkq56ch51cr7048bwkru7vvp925;
       public          postgres    false    219    3522    218            ?
           2606    34221 '   rendez_vous fkrkxjlsiic63q9mqvc1vm616as 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.rendez_vous
    ADD CONSTRAINT fkrkxjlsiic63q9mqvc1vm616as FOREIGN KEY (user_id) REFERENCES public.users(id);
 Q   ALTER TABLE ONLY public.rendez_vous DROP CONSTRAINT fkrkxjlsiic63q9mqvc1vm616as;
       public          postgres    false    220    3546    225                        2606    65903 !   users fkskiuq53qaomjmxowi05vpqaor 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkskiuq53qaomjmxowi05vpqaor FOREIGN KEY (specialite_medecin_id) REFERENCES public.specialite_medecin(id);
 K   ALTER TABLE ONLY public.users DROP CONSTRAINT fkskiuq53qaomjmxowi05vpqaor;
       public          postgres    false    231    225    3560            ?
           2606    34181 #   facture fktbgg1cjysrv97l68p9b40jwdp 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.facture
    ADD CONSTRAINT fktbgg1cjysrv97l68p9b40jwdp FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 M   ALTER TABLE ONLY public.facture DROP CONSTRAINT fktbgg1cjysrv97l68p9b40jwdp;
       public          postgres    false    215    218    3522            	           2606    65915 +   dossier_medical fkthc4ll3xp8bklwqy7j80s3sad 
   FK CONSTRAINT     ?   ALTER TABLE ONLY public.dossier_medical
    ADD CONSTRAINT fkthc4ll3xp8bklwqy7j80s3sad FOREIGN KEY (linked_meta) REFERENCES public.meta(id);
 U   ALTER TABLE ONLY public.dossier_medical DROP CONSTRAINT fkthc4ll3xp8bklwqy7j80s3sad;
       public          postgres    false    3522    232    218           