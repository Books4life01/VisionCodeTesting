import colorsys
def rgb_to_hsv(r, g, b):
    r, g, b = r/255.0, g/255.0, b/255.0
    mx = max(r, g, b)
    mn = min(r, g, b)
    df = mx-mn
    if mx == mn:
        h = 0
    elif mx == r:
        h = (60 * ((g-b)/df) + 360) % 360
    elif mx == g:
        h = (60 * ((b-r)/df) + 120) % 360
    elif mx == b:
        h = (60 * ((r-g)/df) + 240) % 360
    if mx == 0:
        s = 0
    else:
        s = (df/mx)*100
    v = mx*100
    return h, s, v



input = input("RGB")
split = input.replace("(","").replace(")","").split(",")
red = int(split[0])
green = int(split[1])
blue=int(split[2])

hsv = rgb_to_hsv(red, green, blue)
print(hsv)





gimpH = hsv[0]
gimpS = hsv[1]
gimpV = hsv[2]
opencvH = gimpH / 2
opencvS = (gimpS / 100) * 255
opencvV = (gimpV / 100) * 255
print('H: {}\nS: {}\nV: {}\n'.format(opencvH, opencvS, opencvV))

