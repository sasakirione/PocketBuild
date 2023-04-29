import requests

from const import base_url


def get_pokemon_english_name(japanese_name):
    url = f"{base_url}pokemon-species?limit=1000"
    response = requests.get(url)
    data = response.json()

    for species in data["results"]:
        species_url = f"{base_url}pokemon-species/{species['name']}"
        species_response = requests.get(species_url)
        species_data = species_response.json()

        for name_data in species_data["names"]:
            print(f'{name_data["name"]} {name_data["language"]["name"]}')
            if name_data["language"]["name"] == "ja-Hrkt" and name_data["name"] == japanese_name:
                return species["name"]

    raise ValueError(f"Japanese Pokemon name not found: {japanese_name}")


def save_pokemon_english_name():
    url = f"{base_url}pokemon-species?limit=1000"
    response = requests.get(url)
    data = response.json()

    with open("english_name.csv", "w", encoding="utf8") as f:
        for species in data["results"]:
            species_url = f"{base_url}pokemon-species/{species['name']}"
            species_response = requests.get(species_url)
            species_data = species_response.json()
            print(species_data)

            for name_data in species_data["names"]:
                if name_data["language"]["name"] == "ja-Hrkt":
                    f.write(f'{name_data["name"]},{species["name"]}\n')

