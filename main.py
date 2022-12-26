import discord
 
intents = discord.Intents.all()
client = discord.Client(command_prefix='!', intents=intents)
 
@client.event
async def on_ready():
    print('We have logged in as {0.user}'.format(client))
 
@client.event
async def on_message(message):
    if message.author == client.user:
        return

    if message.content.startswith('hello'):
      await message.channel.send ('sudahkah anda meju hari ini?, @everyone')
  
    if message.content.startswith('!t'):
        await message.channel.send('@everyone')
        await message.channel.send('----TUTOR MENJADI ANGGOTA AL MAZRAH----')
        await message.channel.send('1.SIAPKAN BUBUK MESIU')
        await message.channel.send('2.RAKIT SAMPAI SELESAI')
        await message.channel.send('3. SIAPKAN HP NOKIA')
        await message.channel.send('4. DEKATI TARGET')
        await message.channel.send('5. TERIKAH MADERMOSLEM SAMBIL MENEKAN TRIGGER DARI HP NOKIA ANDA')
        await message.channel.send('6.SELAMAT ANDA SUDAH BERHASIL')

   

        
client.run('MTA1MDcxNDgyMzEyNzczMjMwNA.GyZb2C.HMNzdm0a6TlToAtbZRflmdOrW6rMM3fvekTbaE')