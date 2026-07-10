CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    service_id UUID NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_appointment_client FOREIGN KEY (client_id) REFERENCES users (id),
    CONSTRAINT fk_appointment_service FOREIGN KEY (service_id) REFERENCES services (id)
);