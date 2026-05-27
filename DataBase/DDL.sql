ALTER TABLE TBL_EMPLOYEE
   DROP CONSTRAINT FK_TBL_EMPL_RELATIONS_TBL_DEAL;

ALTER TABLE TBL_SALE
   DROP CONSTRAINT FK_TBL_SALE_IS_MADE_B_TBL_CUST;

ALTER TABLE TBL_SALE
   DROP CONSTRAINT FK_TBL_SALE_IS_SOLD_I_TBL_UNIT;

ALTER TABLE TBL_SALE
   DROP CONSTRAINT FK_TBL_SALE_PROCESSES_TBL_EMPL;

ALTER TABLE TBL_SALESGOAL
   DROP CONSTRAINT FK_TBL_SALE_ES_ASIGNA_TBL_EMPL;

ALTER TABLE TBL_SALESGOAL
   DROP CONSTRAINT FK_TBL_SALE_S_ASIGNAD_TBL_DEAL;

ALTER TABLE TBL_UNIT
   DROP CONSTRAINT FK_TBL_UNIT_HAS_TBL_VEHI;

ALTER TABLE TBL_UNIT
   DROP CONSTRAINT FK_TBL_UNIT_OWNS_TBL_DEAL;

DROP TABLE TBL_CUSTOMER CASCADE CONSTRAINTS;

DROP TABLE TBL_DEALERSHIP CASCADE CONSTRAINTS;

DROP INDEX RELATIONSHIP_3_FK;

DROP TABLE TBL_EMPLOYEE CASCADE CONSTRAINTS;

DROP INDEX IS_SOLD_IN2_FK;

DROP INDEX PROCESSES_FK;

DROP INDEX IS_MADE_BY_FK;

DROP TABLE TBL_SALE CASCADE CONSTRAINTS;

DROP INDEX S_ASIGNADA_FK;

DROP INDEX ES_ASIGNADA_FK;

DROP TABLE TBL_SALESGOAL CASCADE CONSTRAINTS;

DROP INDEX IS_SOLD_IN_FK;

DROP INDEX HAS_FK;

DROP INDEX OWNS_FK;

DROP TABLE TBL_UNIT CASCADE CONSTRAINTS;

DROP TABLE TBL_VEHICLE CASCADE CONSTRAINTS;
/*==============================================================*/
/* Table: TBL_CUSTOMER                                          */
/*==============================================================*/
CREATE TABLE TBL_CUSTOMER 
(
   CUS_ID               NUMBER               NOT NULL,
   CUS_NAME             VARCHAR2(50)         NOT NULL,
   CUS_PHONE            VARCHAR2(10)         NOT NULL,
   CUS_EMAIL            VARCHAR2 (50),
   CUS_STATE            VARCHAR2(10),
   CONSTRAINT PK_TBL_CUSTOMER PRIMARY KEY (CUS_ID)
);

/*==============================================================*/
/* Table: TBL_DEALERSHIP                                        */
/*==============================================================*/
CREATE TABLE TBL_DEALERSHIP 
(
   DEA_ID               NUMBER               NOT NULL,
   DEA_NAME             VARCHAR2(50)         NOT NULL,
   DEA_ADDRESS          VARCHAR2(25)         NOT NULL,
   DEA_PHONE            VARCHAR2(10)         NOT NULL,
   DEA_STATE            VARCHAR2(10),
   CONSTRAINT PK_TBL_DEALERSHIP PRIMARY KEY (DEA_ID)
);

/*==============================================================*/
/* Table: TBL_EMPLOYEE                                          */
/*==============================================================*/
CREATE TABLE TBL_EMPLOYEE 
(
   EMP_ID               NUMBER               NOT NULL,
   DEA_ID               NUMBER               NOT NULL,
   EMP_NAME             VARCHAR2(50)         NOT NULL,
   EMP_PHONE            VARCHAR2(10)         NOT NULL,
   EMP_SALARY           NUMBER(8)            NOT NULL,
   EMP_HIRE_DATE        DATE,
   EMP_ROLE             VARCHAR2(7)          NOT NULL
      CONSTRAINT CKC_EMP_ROLE_TBL_EMPL CHECK (EMP_ROLE IN ('manager','seller')),
   EMP_STATE            VARCHAR2(10),
   CONSTRAINT PK_TBL_EMPLOYEE PRIMARY KEY (EMP_ID)
);

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
CREATE INDEX RELATIONSHIP_3_FK ON TBL_EMPLOYEE (DEA_ID ASC);

/*==============================================================*/
/* Table: TBL_SALE                                              */
/*==============================================================*/
CREATE TABLE TBL_SALE 
(
   SALE_ID              NUMBER               NOT NULL,
   CUS_ID               NUMBER               NOT NULL,
   EMP_ID               NUMBER               NOT NULL,
   UNIT_ID              NUMBER               NOT NULL,
   SALE_DATE_START      DATE                 NOT NULL,
   SALE_PRICE           NUMBER(9,2)          NOT NULL,
   SALE_STATUS          VARCHAR2(10)          NOT NULL
      CONSTRAINT CKC_SALE_STATUS_TBL_SALE CHECK (SALE_STATUS IN ('confirmed','cancelled','inprogress')),
   SALE_DATE_END        DATE,
   CONSTRAINT PK_TBL_SALE PRIMARY KEY (SALE_ID)
);

/*==============================================================*/
/* Index: IS_MADE_BY_FK                                         */
/*==============================================================*/
CREATE INDEX IS_MADE_BY_FK ON TBL_SALE (CUS_ID ASC);
/*==============================================================*/
/* Index: PROCESSES_FK                                          */
/*==============================================================*/
CREATE INDEX PROCESSES_FK ON TBL_SALE (EMP_ID ASC);

/*==============================================================*/
/* Index: IS_SOLD_IN2_FK                                        */
/*==============================================================*/
CREATE INDEX IS_SOLD_IN2_FK ON TBL_SALE (UNIT_ID ASC);

/*==============================================================*/
/* Table: TBL_SALESGOAL                                         */
/*==============================================================*/
CREATE TABLE TBL_SALESGOAL 
(
   SGL_ID               NUMBER               NOT NULL,
   EMP_ID               NUMBER,
   DEA_ID               NUMBER               NOT NULL,
   SGL_GOAL_TYPE        VARCHAR2(9)         
      CONSTRAINT CKC_SGL_GOAL_TYPE_TBL_SALE CHECK (SGL_GOAL_TYPE IS NULL OR (SGL_GOAL_TYPE IN ('monthly','quarterly','yearly'))),
   SGL_TARGET_VALUE     NUMBER(15,3)         NOT NULL,
   SGL_START_DATE       DATE                 NOT NULL,
   SGL_END_DATE         DATE                 NOT NULL,
   SGL_STATE            VARCHAR2(10)         NOT NULL,
   CONSTRAINT PK_TBL_SALESGOAL PRIMARY KEY (SGL_ID)
);

/*==============================================================*/
/* Index: ES_ASIGNADA_FK                                        */
/*==============================================================*/
CREATE INDEX ES_ASIGNADA_FK ON TBL_SALESGOAL (EMP_ID ASC);

/*==============================================================*/
/* Index: S_ASIGNADA_FK                                         */
/*==============================================================*/
CREATE INDEX S_ASIGNADA_FK ON TBL_SALESGOAL (DEA_ID ASC);

/*==============================================================*/
/* Table: TBL_UNIT                                              */
/*==============================================================*/
CREATE TABLE TBL_UNIT 
(
   UNIT_ID              NUMBER               NOT NULL,
   DEA_ID               NUMBER               NOT NULL,
   VEH_ID               NUMBER               NOT NULL,
   UNIT_LICENSE_PLATE   VARCHAR2(6)          NOT NULL,
   UNIT_COLOR           VARCHAR2 (10)        NOT NULL,
   UNIT_MILEAGE         NUMBER,
   UNIT_DATE_ENTRY      DATE                 NOT NULL,
   UNIT_CONDITION       VARCHAR2(4)          NOT NULL
    CONSTRAINT CKC_UNIT_CONDITION_TBL_UNIT CHECK (UNIT_CONDITION IN ('used','new')),
   UNIT_STATUS          VARCHAR2(9)          NOT NULL
    CONSTRAINT CKC_UNIT_STATUS_TBL_UNIT CHECK (UNIT_STATUS IN ('available','reserved','sold')),
   CONSTRAINT UK_UNIT_LICENSE_PLATE UNIQUE (UNIT_LICENSE_PLATE),
   CONSTRAINT PK_TBL_UNIT PRIMARY KEY (UNIT_ID)
);

/*==============================================================*/
/* Index: OWNS_FK                                               */
/*==============================================================*/
CREATE INDEX OWNS_FK ON TBL_UNIT (DEA_ID ASC);

/*==============================================================*/
/* Index: HAS_FK                                                */
/*==============================================================*/
CREATE INDEX HAS_FK ON TBL_UNIT (VEH_ID ASC);

/*==============================================================*/
/* Table: TBL_VEHICLE                                           */
/*==============================================================*/
CREATE TABLE TBL_VEHICLE 
(
   VEH_ID               NUMBER               NOT NULL,
   VEH_BRAND            VARCHAR2(25)         NOT NULL,
   VEH_MODEL            VARCHAR2 (25)        NOT NULL,
   VEH_YEAR             NUMBER,
   VEH_BODY_TYPE        VARCHAR2(25)         NOT NULL,
   VEH_FUEL_TYPE        VARCHAR2(8)          NOT NULL
      CONSTRAINT CKC_VEH_FUEL_TYPE_TBL_VEHI CHECK (VEH_FUEL_TYPE IN ('electric','gasoline','hybrid')),
   VEH_CATEGORY         VARCHAR2(8)          NOT NULL
      CONSTRAINT CKC_VEH_CATEGORY_TBL_VEHI CHECK (VEH_CATEGORY IN ('standard','luxury')),
   VEH_STATE            VARCHAR2(10),
   CONSTRAINT PK_TBL_VEHICLE PRIMARY KEY (VEH_ID)
);

ALTER TABLE TBL_EMPLOYEE
   ADD CONSTRAINT FK_TBL_EMPL_RELATIONS_TBL_DEAL FOREIGN KEY (DEA_ID)
      REFERENCES TBL_DEALERSHIP (DEA_ID);

ALTER TABLE TBL_SALE
   ADD CONSTRAINT FK_TBL_SALE_IS_MADE_B_TBL_CUST FOREIGN KEY (CUS_ID)
      REFERENCES TBL_CUSTOMER (CUS_ID);

ALTER TABLE TBL_SALE
   ADD CONSTRAINT FK_TBL_SALE_IS_SOLD_I_TBL_UNIT FOREIGN KEY (UNIT_ID)
      REFERENCES TBL_UNIT (UNIT_ID);

ALTER TABLE TBL_SALE
   ADD CONSTRAINT FK_TBL_SALE_PROCESSES_TBL_EMPL FOREIGN KEY (EMP_ID)
      REFERENCES TBL_EMPLOYEE (EMP_ID);

ALTER TABLE TBL_SALESGOAL
   ADD CONSTRAINT FK_TBL_SALE_ES_ASIGNA_TBL_EMPL FOREIGN KEY (EMP_ID)
      REFERENCES TBL_EMPLOYEE (EMP_ID);

ALTER TABLE TBL_SALESGOAL
   ADD CONSTRAINT FK_TBL_SALE_S_ASIGNAD_TBL_DEAL FOREIGN KEY (DEA_ID)
      REFERENCES TBL_DEALERSHIP (DEA_ID);

ALTER TABLE TBL_UNIT
   ADD CONSTRAINT FK_TBL_UNIT_HAS_TBL_VEHI FOREIGN KEY (VEH_ID)
      REFERENCES TBL_VEHICLE (VEH_ID);

ALTER TABLE TBL_UNIT
   ADD CONSTRAINT FK_TBL_UNIT_OWNS_TBL_DEAL FOREIGN KEY (DEA_ID)
      REFERENCES TBL_DEALERSHIP (DEA_ID);
      
CREATE UNIQUE INDEX ux_one_manager ON TBL_EMPLOYEE(
   CASE
      WHEN UPPER(emp_role) = 'MANAGER' THEN dea_id
      ELSE NULL
   END
);

ALTER TABLE TBL_DEALERSHIP ADD CONSTRAINT UK_DEA_PHONE UNIQUE (DEA_PHONE);

ALTER TABLE TBL_EMPLOYEE ADD CONSTRAINT UK_EMP_PHONE UNIQUE (EMP_PHONE);

ALTER TABLE TBL_CUSTOMER ADD CONSTRAINT UK_CUS_PHONE UNIQUE (CUS_PHONE);

ALTER TABLE TBL_CUSTOMER ADD CONSTRAINT UK_CUS_EMAIL UNIQUE (CUS_EMAIL);

/*==============================================================*/
/*Debido a que Vehicle es un modelo abstracto                   */
/* Evita duplicar modelos identicos                             */
/*==============================================================*/

ALTER TABLE TBL_VEHICLE ADD CONSTRAINT UK_VEHICLE_MODEL
UNIQUE (VEH_BRAND,VEH_MODEL,VEH_YEAR,VEH_BODY_TYPE,VEH_FUEL_TYPE,VEH_CATEGORY);