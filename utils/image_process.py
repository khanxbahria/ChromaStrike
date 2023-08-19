import argparse
import threading
import tkinter as tk
import cv2
import numpy as np

parser = argparse.ArgumentParser(description='Color Thresholding Operations')
parser.add_argument('--path', help='Path to image', required=True)
args = parser.parse_args()

initial_lower_h=140
initial_lower_s=120
initial_lower_v=180
initial_upper_h=150
initial_upper_s=255
initial_upper_v=255

global purp_mask
def img_process():
    image = cv2.imread(args.path)
    hsvFrame = cv2.cvtColor(image, cv2.COLOR_BGR2HSV)
    while True:
        global purp_mask
        purp_lower = np.array([hl_comp.get(), sl_comp.get(), vl_comp.get()], np.uint8)
        purp_upper = np.array([hu_comp.get(), su_comp.get(), vu_comp.get()], np.uint8)
        purp_mask = cv2.inRange(hsvFrame, purp_lower, purp_upper)

        result = cv2.bitwise_and(image, image, mask= purp_mask)
        resized_mask = cv2.resize(purp_mask, (600, 600))
        cv2.imshow("masked", result)
        cv2.imshow("orig", image)
        cv2.imshow("mask", resized_mask)
        # Press Q on keyboard to  exit
        if cv2.waitKey(25) & 0xFF == ord('q'):
          break
     

    cv2.destroyAllWindows()


app = tk.Tk()
app.geometry("500x500")

hl_comp = tk.Scale(app, from_=0, to=180, orient=tk.HORIZONTAL)
hl_comp.pack(expand=True, fill=tk.BOTH)
tk.Label(app, text="H lower").pack()

sl_comp = tk.Scale(app, from_=0, to=255, orient=tk.HORIZONTAL)
sl_comp.pack(expand=True, fill=tk.BOTH)
tk.Label(app, text="S lower").pack()

vl_comp = tk.Scale(app, from_=0, to=255, orient=tk.HORIZONTAL)
vl_comp.pack(expand=True, fill=tk.BOTH)
tk.Label(app, text="V lower").pack()

hu_comp = tk.Scale(app, from_=0, to=180, orient=tk.HORIZONTAL)
hu_comp.pack(expand=True, fill=tk.BOTH)
tk.Label(app, text="H upper").pack()

su_comp = tk.Scale(app, from_=0, to=255, orient=tk.HORIZONTAL)
su_comp.pack(expand=True, fill=tk.BOTH)
tk.Label(app, text="S upper").pack()

vu_comp = tk.Scale(app, from_=0, to=255, orient=tk.HORIZONTAL)
vu_comp.pack(expand=True, fill=tk.BOTH)
tk.Label(app, text="V upper").pack()


hl_comp.set(initial_lower_h)
sl_comp.set(initial_lower_s)
vl_comp.set(initial_lower_v)

hu_comp.set(initial_upper_h)
su_comp.set(initial_upper_s)
vu_comp.set(initial_upper_v)



img_process_thread = threading.Thread(target=img_process)
img_process_thread.start()




app.mainloop()
img_process_thread.join()

cv2.imwrite("result.png", purp_mask)