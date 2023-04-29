import csv


def make_abilities_sql():
    with open("abilities.csv", encoding="utf8") as f:
        reader = csv.reader(f)
        for row in reader:
            ability_id, name, generation = row
            sql = f"INSERT INTO abilities (id, name, generation) VALUES ({ability_id}, '{name}', {generation});"
            with open("../../db/data/abilities.sql", "a", encoding="utf8") as f2:
                f2.write(sql + "\n")
