import csv
import requests


def save_pokemon_csv():
    # 後者のCSVファイルから日本語の「identifier」を取得するための辞書を作成
    pokemon_names = {}
    with open("./pokeapi_data/pokemon_species_names.csv", encoding="utf8") as f:
        reader = csv.reader(f)
        next(reader)  # header行を飛ばす
        for row in reader:
            pokemon_species_id, local_language_id, name, genus = row
            if local_language_id == "1":
                pokemon_names[pokemon_species_id] = name

    # 前者のCSVファイルを読み込み、必要な情報を抽出して出力する
    with open("./pokeapi_data/pokemons.csv", encoding="utf8") as f:
        reader = csv.reader(f)
        next(reader)  # header行を飛ばす
        for row in reader:
            pokemon_id, identifier, species_id, height, weight, base_experience, order, is_default = row
            name = pokemon_names.get(species_id)
            if name is None:
                continue
            with open("pokemon.csv", mode="a", encoding="utf8") as f2:
                writer = csv.writer(f2)
                writer.writerow([pokemon_id, identifier, name, species_id, is_default])


def add_gen_no():
    csv1_url = 'https://raw.githubusercontent.com/PokeAPI/pokeapi/master/data/v2/csv/pokemon_form_generations.csv'
    csv2_filepath = 'pokemon.csv'

    # CSV1をURLから取得
    response = requests.get(csv1_url)
    response.raise_for_status()
    csv1_content = response.text

    # CSV1をパースしてデータを整理する
    csv1_lines = csv1_content.split('\n')[1:]  # ヘッダー行をスキップ
    csv1_data = [line.split(',') for line in csv1_lines]

    min_generations = {}
    for row in csv1_data:
        if len(row) != 3:
            continue
        pokemon_form_id, generation_id, _ = row
        pokemon_form_id = int(pokemon_form_id)
        generation_id = int(generation_id)
        if pokemon_form_id not in min_generations:
            min_generations[pokemon_form_id] = generation_id
        else:
            min_generations[pokemon_form_id] = min(min_generations[pokemon_form_id], generation_id)

    # CSV2をファイルパスから読み込む
    with open(csv2_filepath, 'r') as f:
        csv2_reader = csv.reader(f)
        csv2_data = [row for row in csv2_reader]

    # CSV3を生成する
    csv3_lines = []
    for row in csv2_data:
        pokemon_form_id = int(row[0])
        if pokemon_form_id in min_generations:
            row.append(str(min_generations[pokemon_form_id]))
        else:
            row.append('9')  # 対応するデータがない場合は9を追加
        csv3_lines.append(','.join(row))

    csv3 = '\n'.join(csv3_lines)
    with open('pokemon2.csv', 'w') as f:
        f.write(csv3)
