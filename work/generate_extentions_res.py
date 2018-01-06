from jinja2 import Environment, FileSystemLoader

data = {
    "extensionInfoList": [
        {"ext": "apk", "mimeTypes": ["application/vnd.android.package-archive"]}
        , {"ext": "pdf", "mimeTypes": ["application/pdf"]}
        , {"ext": "tgz", "mimeTypes": []}
        , {"ext": "zip", "mimeTypes": []}
    ]
}

xmlTemplates = [
    {
        "template": "template.AndroidManifest.xml",
        "output": "../app/src/main/AndroidManifest.xml"
    },
    {
        "template": "template.strings_ext.xml",
        "output": "../app/src/main/res/values/strings_ext.xml"
    },
    {
        "template": "template.pref_general.xml",
        "output": "../app/src/main/res/xml/pref_general.xml"
    },
]
javaTemplates = [
    {
        "template": "template.ConfirmActivity.java",
        "output": "../app/src/main/java/com/nagopy/android/downloadconfirm/extension/{}ConfirmActivity.java"
    },
    {
        "template": "template.HookTest.java",
        "output": "../app/src/androidTest/java/com/nagopy/android/downloadconfirm/extension/{}HookTest.java"
    },
]

env = Environment(loader=FileSystemLoader('.'))

for xmlTemplate in xmlTemplates:
    template = env.get_template(xmlTemplate['template'])
    rendered = template.render(data)

    with open(xmlTemplate['output'], 'w') as f:
        f.write(rendered)
        f.close()

for javaTemplate in javaTemplates:
    for extInfo in data['extensionInfoList']:
        template = env.get_template(javaTemplate['template'])
        rendered = template.render({'extInfo': extInfo})

        with open(javaTemplate['output'].format(extInfo['ext'].capitalize()), 'w') as f:
            f.write(rendered)
            f.close()
