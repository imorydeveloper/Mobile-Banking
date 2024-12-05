CREATE TABLE account_types
(
    id            INT AUTO_INCREMENT NOT NULL,
    alias         VARCHAR(50)        NOT NULL,
    name          VARCHAR(50)        NOT NULL,
    `description` TEXT               NULL,
    is_deleted    BIT(1)             NOT NULL,
    CONSTRAINT pk_account_types PRIMARY KEY (id)
);

CREATE TABLE accounts
(
    id              INT AUTO_INCREMENT NOT NULL,
    alias           VARCHAR(255)       NULL,
    act_no          VARCHAR(255)       NULL,
    act_name        VARCHAR(255)       NULL,
    balance         DECIMAL            NULL,
    transfer_limit  DECIMAL            NULL,
    is_hidden       BIT(1)             NULL,
    card_id         INT                NULL,
    account_type_id INT                NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id)
);

CREATE TABLE authorities
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)       NULL,
    CONSTRAINT pk_authorities PRIMARY KEY (id)
);

CREATE TABLE card_types
(
    id         INT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(50)        NOT NULL,
    is_deleted BIT(1)             NULL,
    CONSTRAINT pk_card_types PRIMARY KEY (id)
);

CREATE TABLE cards
(
    id           INT AUTO_INCREMENT NOT NULL,
    number       VARCHAR(12)        NOT NULL,
    holder       VARCHAR(100)       NOT NULL,
    cvv          INT                NOT NULL,
    issue_at     date               NOT NULL,
    expired_at   date               NOT NULL,
    is_deleted   BIT(1)             NOT NULL,
    card_type_id INT                NULL,
    CONSTRAINT pk_cards PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id   INT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)       NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

CREATE TABLE roles_authorities
(
    authorities_id INT NOT NULL,
    roles_id       INT NOT NULL
);

CREATE TABLE transactions
(
    id                INT AUTO_INCREMENT NOT NULL,
    amount            DECIMAL            NOT NULL,
    transaction_at    datetime           NULL,
    transaction_types VARCHAR(30)        NOT NULL,
    payment_receiver  VARCHAR(255)       NULL,
    status            BIT(1)             NOT NULL,
    is_deleted        BIT(1)             NOT NULL,
    owner_id          INT                NULL,
    receiver_id       INT                NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

CREATE TABLE user_accounts
(
    account_id INT NOT NULL,
    user_id    INT NULL,
    CONSTRAINT pk_user_accounts PRIMARY KEY (account_id)
);

CREATE TABLE user_roles
(
    role_id  INT NOT NULL,
    users_id INT NOT NULL
);

CREATE TABLE users
(
    user_id               INT AUTO_INCREMENT NOT NULL,
    uuid                  VARCHAR(255)       NOT NULL,
    national_card_id      VARCHAR(20)        NOT NULL,
    name                  VARCHAR(100)       NOT NULL,
    gender                VARCHAR(10)        NOT NULL,
    phone_number          VARCHAR(10)        NOT NULL,
    email                 VARCHAR(40)        NOT NULL,
    pin                   INT                NOT NULL,
    password              VARCHAR(255)       NOT NULL,
    profile_image         VARCHAR(255)       NULL,
    student_id_card       VARCHAR(20)        NULL,
    street                VARCHAR(255)       NULL,
    village               VARCHAR(255)       NULL,
    commune               VARCHAR(255)       NULL,
    district              VARCHAR(255)       NULL,
    city                  VARCHAR(255)       NULL,
    position              VARCHAR(255)       NULL,
    employee_type         VARCHAR(255)       NULL,
    company_name          VARCHAR(255)       NULL,
    monthly_income_range  DECIMAL            NULL,
    main_source_of_income VARCHAR(255)       NULL,
    is_blocked            BIT(1)             NULL,
    is_deleted            BIT(1)             NULL,
    CONSTRAINT pk_users PRIMARY KEY (user_id)
);

ALTER TABLE users
ADD column is_verified BOOLEAN after is_deleted;

CREATE TABLE user_verifications
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    verified_code VARCHAR(255)          NULL,
    expiry_time   time                  NULL,
    user_user_id  INT                   NULL,
    CONSTRAINT pk_user_verifications PRIMARY KEY (id)
);