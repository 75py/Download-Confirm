# -*- coding: utf-8 -*-

import string
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.cidfonts import UnicodeCIDFont
from reportlab.pdfgen import canvas


def alphabets(length):
    ret = ""
    for iii in range(0, length + 1):
        ret += string.ascii_lowercase[iii]
    return ret


# http://o-tomox.hatenablog.com/entry/2013/07/22/221158
def make(filename="pdftest"):
    pdf_canvas = canvas.Canvas("./{0}.pdf".format(filename), bottomup=False)
    pdf_canvas.setAuthor("75py")
    pdf_canvas.setTitle("PDF sample {0}".format(filename))
    pdf_canvas.setSubject("PDF sample {0}".format(filename))

    pdfmetrics.registerFont(UnicodeCIDFont("HeiseiKakuGo-W5"))
    pdf_canvas.setFont("HeiseiKakuGo-W5", 45)
    pdf_canvas.drawString(50, 100, "PDF sample")
    y = 150
    for str in filename.split("."):
        pdf_canvas.drawString(50, y, str)
        y += 50
    pdf_canvas.save()


if __name__ == '__main__':
    filename = ""
    for i in range(0, 10):
        filename = "testpdf"
        for k in range(i):
            filename += '.'
            filename += alphabets(k)
        make(filename)
