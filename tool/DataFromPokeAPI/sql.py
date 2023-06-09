import csv


def make_abilities_sql():
    with open("abilities.csv", encoding="utf8") as f:
        reader = csv.reader(f)
        for row in reader:
            ability_id, name, generation = row
            sql = f"INSERT INTO abilities (id, name, generation) VALUES ({ability_id}, '{name}', {generation});"
            with open("../../db/data/abilities.sql", "a", encoding="utf8") as f2:
                f2.write(sql + "\n")


def make_pokemon_sql_without_other_form():
    with open("pokemon2.csv", encoding="utf8") as f:
        reader = csv.reader(f)
        for row in reader:
            pokemon_id, identifier, name, species_id, is_default, gen = row
            if is_default == "0":
                continue
            sql = f"INSERT INTO Pkemons (id, dex_no, form_no, name, form_name, generation) VALUES ({pokemon_id}, '{pokemon_id}', 1, '{name}', '', {gen});"
            with open("../../db/data/pokemons1.sql", "a", encoding="utf8") as f2:
                f2.write(sql + "\n")


def make_moves_sql():
    # CSVファイルを読み込み
    with open("moves.csv", "r", encoding="utf8") as f:
        csv_reader = csv.DictReader(f)
        csv_data = [row for row in csv_reader]

    # INSERT文のリストを作成
    insert_statements = []

    for row in csv_data:
        statement = f"INSERT INTO moves (id, name, type, category, generation) VALUES ({row['id']}, '{row['name']}', " \
                    f"{row['type_id']}, {row['damage_class_id']}, {row['generation_id']});"
        insert_statements.append(statement)

    with open("../../db/data/moves.sql", "w", encoding="utf8") as f:
        for statement in insert_statements:
            f.write(statement + "\n")
