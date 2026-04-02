CREATE TABLE professors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE terms (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(10) NOT NULL UNIQUE,
    semester VARCHAR(10) NOT NULL,
    year INTEGER NOT NULL
);

CREATE TABLE offerings (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT NOT NULL,
    term_id BIGINT NOT NULL,
    professor_id BIGINT,
    section VARCHAR(20),
    students_count INTEGER,
    letter_grade_count INTEGER,
    median NUMERIC(5,2),
    mode NUMERIC(5,2),
    CONSTRAINT uk_offering_course_term_prof_section
        UNIQUE (course_id, term_id, professor_id, section),
    CONSTRAINT fk_offerings_course
        FOREIGN KEY (course_id)
        REFERENCES courses(id),
    CONSTRAINT fk_offerings_term
        FOREIGN KEY (term_id)
        REFERENCES terms(id),
    CONSTRAINT fk_offerings_professor
        FOREIGN KEY (professor_id)
        REFERENCES professors(id)
);

CREATE INDEX idx_offerings_course_id ON offerings(course_id);
CREATE INDEX idx_offerings_term_id ON offerings(term_id);
CREATE INDEX idx_offerings_professor_id ON offerings(professor_id);
