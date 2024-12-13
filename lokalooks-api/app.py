import qrcode
from IPython.display import display, Image

DESTINATIONS = [
    {"name": "Owabong Water Park Purbalingga", "ticket_price": 25000, "souvenir_budget": 30000, "fuel_cost": 20000,
     "image_url": "https://asset.kompas.com/crops/CKgdCralAFr_S9_zlh18AX83f_8=/0x0:0x0/1200x800/data/photo/2021/06/21/60d042d9bcc3e.jpg",
     "info_url": "https://owabong.com"},
    {"name": "Taman Wisata Pendidikan Purbasari Pancuran Mas", "ticket_price": 27000, "souvenir_budget": 20000, "fuel_cost": 20000,
     "image_url": "https://chse.kemenparekraf.go.id/storage/app/uploads/public/615/27f/202/61527f202dd5d695160860.jpg",
     "info_url": "https://purbasaripancuranmas.com"},
    {"name": "Sanggaluri Park Purbalingga", "ticket_price": 23000, "souvenir_budget": 20000, "fuel_cost": 80000,
     "image_url": "https://visitjawatengah.jatengprov.go.id/assets/images/b718f5bc-6b98-45bd-b4be-1e5c7246f11f.jpeg",
     "info_url": "https://sanggaluri.com"},
    {"name": "Curug Sumba", "ticket_price": 5000, "souvenir_budget": 3000, "fuel_cost": 20000,
     "image_url": "https://thumb.tvonenews.com/thumbnail/2022/02/22/621440a1ec5e6-pemandangan-sempurna-di-curug-sumba-bisa-didapatkan-saat-musim-kemarau_1265_711.jpg",
     "info_url": "https://curugsumba.com"},
]

def recommend_destinations(budget, people):
    """
    Merekomendasikan destinasi wisata yang sesuai dengan anggaran.

    Parameters:
        budget (int): Total dana yang dimiliki.
        people (int): Jumlah orang yang akan bepergian.

    Returns:
        list: Daftar destinasi wisata yang sesuai dengan anggaran.
    """
    suitable_destinations = []
    for destination in DESTINATIONS:
        total_cost = (
            (destination["ticket_price"] * people) +
            destination["souvenir_budget"] +
            destination["fuel_cost"]
        )
        if total_cost <= budget:
            suitable_destinations.append({
                "name": destination["name"],
                "total_cost": total_cost,
                "image_url": destination["image_url"],
                "info_url": destination["info_url"]
            })

    if not suitable_destinations:
        return "Maaf, tidak ada destinasi yang sesuai dengan anggaran Anda."

    return sorted(suitable_destinations, key=lambda x: x["total_cost"])

def generate_qr_code(url):
    """
    Menghasilkan QR code dari URL.

    Parameters:
        url (str): URL yang akan dienkode ke dalam QR code.

    Returns:
        None
    """
    qr = qrcode.QRCode(
        version=1,
        error_correction=qrcode.constants.ERROR_CORRECT_L,
        box_size=10,
        border=4,
    )
    qr.add_data(url)
    qr.make(fit=True)

    img = qr.make_image(fill="black", back_color="white")
    display(img)

# Contoh penggunaan
user_budget = int(input("Masukkan total dana Anda: "))
user_people = int(input("Masukkan jumlah orang: "))

result = recommend_destinations(user_budget, user_people)
if isinstance(result, str):
    print(result)
else:
    print("Destinasi yang sesuai:")
    for dest in result:
        print(f"- {dest['name']} (Total Biaya: Rp {dest['total_cost']})")
        # Display the image of the destination
        display(Image(url=dest['image_url']))
        print(f"Kunjungi untuk informasi lebih lanjut: {dest['info_url']}")
        # Generate and display QR code
        print("Scan QR code untuk informasi lebih lanjut:")
        generate_qr_code(dest['info_url'])