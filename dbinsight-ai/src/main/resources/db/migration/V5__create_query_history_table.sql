CREATE TABLE query_history (

                               id BIGSERIAL PRIMARY KEY,

                               original_query TEXT NOT NULL,

                               query_type VARCHAR(50),

                               score INTEGER,

                               issues TEXT,

                               suggestions TEXT,

                               created_at TIMESTAMP NOT NULL,

                               user_id BIGINT NOT NULL,

                               CONSTRAINT fk_query_user
                                   FOREIGN KEY(user_id)
                                       REFERENCES users(id)
                                       ON DELETE CASCADE
);