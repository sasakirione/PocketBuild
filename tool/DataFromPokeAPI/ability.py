import csv


def save_ability_english_name():
    # 前者のCSVファイルから日本語の「identifier」を取得するための辞書を作成
    ability_names = {}
    with open("./pokeapi_data/ability_names.csv", encoding="utf8") as f:
        reader = csv.reader(f)
        next(reader)  # header行を飛ばす
        for row in reader:
            ability_id, local_language_id, name = row
            if local_language_id == "1":
                ability_names[ability_id] = name

    # 後者のCSVファイルを読み込み、必要な情報を抽出して出力する
    with open("./pokeapi_data/abilities.csv", encoding="utf8") as f:
        reader = csv.reader(f)
        next(reader)  # header行を飛ばす
        for row in reader:
            ability_id, identifier, generation_id, is_main_series = row
            if is_main_series == "0":
                continu
            name = ability_names.get(ability_id)
            if name is None:
                continue
            open("abilities.csv", "a", encoding="utf8").write(f"{name},{generation_id}\n")
