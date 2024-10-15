CREATE TABLE medications
(
    id_medication   SERIAL PRIMARY KEY,
    id_patient      BIGINT               NOT NULL,
    medication_name VARCHAR(255)         NOT NULL,
    dosage          VARCHAR(255)         NOT NULL,
    frequency       VARCHAR(100)         NOT NULL,
    treatment_start DATE                 NOT NULL,
    treatment_end   DATE,
    active          BOOLEAN DEFAULT TRUE NOT NULL,

    CONSTRAINT fk_medications_patient
        FOREIGN KEY (id_patient)
            REFERENCES patients (id_patient)
            ON DELETE CASCADE
);

CREATE TABLE measurements
(
    id_measurement     SERIAL PRIMARY KEY,
    id_patient         BIGINT               NOT NULL,
    measurement_date   TIMESTAMP            NOT NULL,
    systolic_pressure  INT                  NOT NULL,
    diastolic_pressure INT                  NOT NULL,
    heart_rate         INT                  NOT NULL,
    notes              TEXT,
    active             BOOLEAN DEFAULT TRUE NOT NULL,

    CONSTRAINT fk_measurements_patient
        FOREIGN KEY (id_patient)
            REFERENCES patients (id_patient)
            ON DELETE CASCADE
);