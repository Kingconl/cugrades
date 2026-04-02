CREATE TABLE grade_distributions (
    id BIGSERIAL PRIMARY KEY,
    offering_id BIGINT NOT NULL,
    grade_label VARCHAR(10) NOT NULL,
    count INTEGER NOT NULL,
    CONSTRAINT uk_grade_distribution_offering_label
        UNIQUE (offering_id, grade_label),
    CONSTRAINT fk_grade_distributions_offering
        FOREIGN KEY (offering_id)
        REFERENCES offerings(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_grade_distributions_offering_id ON grade_distributions(offering_id);
CREATE INDEX idx_grade_distributions_offering_grade_label ON grade_distributions(offering_id, grade_label);
