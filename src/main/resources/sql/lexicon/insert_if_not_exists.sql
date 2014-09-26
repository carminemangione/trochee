INSERT INTO trochee.lexicon (trochee)
WITH candidate AS (
SELECT unnest(?::VARCHAR[]) as trochee
)
SELECT
    c.trochee
FROM candidate c
LEFT JOIN trochee.lexicon l ON c.trochee = l.trochee
WHERE l.trochee IS NULL