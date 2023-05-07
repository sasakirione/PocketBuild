import csv
from io import StringIO

import requests

from const import base_url


def get_moves(pokemon_name, soft_name):
    url = f"{base_url}pokemon/{pokemon_name}"
    response = requests.get(url)
    data = response.json()

    moves = []
    for move_data in data["moves"]:
        for version_group_details in move_data["version_group_details"]:
            if version_group_details["version_group"]["name"] == f"{soft_name}":
                move_name = move_data["move"]["name"]
                moves.append(move_name)
    return moves


def get_japanese_moves(moves):
    japanese_moves = []
    for move_name in moves:
        url = f"{base_url}move/{move_name}"
        response = requests.get(url)
        data = response.json()

        for name_data in data["names"]:
            if name_data["language"]["name"] == "ja-Hrkt":
                japanese_moves.append(name_data["name"])
                break
    return japanese_moves


def make_move_csv():
    # CSV1のURL
    csv1_url = "https://raw.githubusercontent.com/PokeAPI/pokeapi/master/data/v2/csv/moves.csv"

    # CSV1をHTTP経由で読み込み
    response = requests.get(csv1_url)
    response.raise_for_status()
    csv1_reader = csv.DictReader(StringIO(response.text))
    csv1_data = [row for row in csv1_reader]

    # CSV2のURL
    csv2_url = "https://raw.githubusercontent.com/PokeAPI/pokeapi/master/data/v2/csv/move_names.csv"

    # CSV2をHTTP経由で読み込み
    response = requests.get(csv2_url)
    response.raise_for_status()
    csv2_reader = csv.DictReader(StringIO(response.text))
    csv2_data = [row for row in csv2_reader]

    # CSV3のデータを作成
    csv3_data = []

    for row1 in csv1_data:
        if row1["type_id"] == "10002":
            continue
        for row2 in csv2_data:
            if row1["id"] == row2["move_id"] and row2["local_language_id"] == "1":
                csv3_data.append({
                    "id": row1["id"],
                    "name": row2["name"],
                    "type_id": row1["type_id"],
                    "damage_class_id": row1["damage_class_id"],
                    "generation_id": row1["generation_id"]
                })
                break

    # CSV3を書き出し
    with open("moves.csv", "w", newline='', encoding="utf8") as f:
        fieldnames = ["id", "name", "type_id", "damage_class_id", "generation_id"]
        csv3_writer = csv.DictWriter(f, fieldnames=fieldnames)

        csv3_writer.writeheader()
        csv3_writer.writerows(csv3_data)