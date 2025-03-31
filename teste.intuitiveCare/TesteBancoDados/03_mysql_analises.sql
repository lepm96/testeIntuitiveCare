USE ans_analytics;


WITH ultimo_trimestre AS (
    SELECT MAX(competencia) AS max_competencia FROM demonstracoes_contabeis
)
SELECT
    o.razao_social,
    o.nome_fantasia,
    SUM(d.valor) AS total_despesas
FROM demonstracoes_contabeis d
         JOIN operadoras_ativas o ON d.registro_ans = o.registro_ans
         JOIN ultimo_trimestre ut ON d.competencia >= DATE_SUB(ut.max_competencia, INTERVAL 3 MONTH)
WHERE d.descricao_conta LIKE '%EVENTOS/%SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
GROUP BY o.razao_social, o.nome_fantasia
ORDER BY total_despesas DESC
    LIMIT 10;

WITH ultimo_ano AS (
    SELECT MAX(competencia) AS max_competencia FROM demonstracoes_contabeis
)
SELECT
    o.razao_social,
    o.nome_fantasia,
    SUM(d.valor) AS total_despesas
FROM demonstracoes_contabeis d
         JOIN operadoras_ativas o ON d.registro_ans = o.registro_ans
         JOIN ultimo_ano ua ON d.competencia >= DATE_SUB(ua.max_competencia, INTERVAL 1 YEAR)
WHERE d.descricao_conta LIKE '%EVENTOS/%SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
GROUP BY o.razao_social, o.nome_fantasia
ORDER BY total_despesas DESC
    LIMIT 10;