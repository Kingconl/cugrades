CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY,
    subject_id BIGINT NOT NULL,
    course_number VARCHAR(10) NOT NULL,
    title VARCHAR(255),
    CONSTRAINT uk_courses_subject_course_number UNIQUE (subject_id, course_number),
    CONSTRAINT fk_courses_subject
        FOREIGN KEY (subject_id)
        REFERENCES subjects(id)
);

CREATE INDEX idx_courses_subject_id ON courses(subject_id);
CREATE INDEX idx_courses_subject_course_number ON courses(subject_id, course_number);
