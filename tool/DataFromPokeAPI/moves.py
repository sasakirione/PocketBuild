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
