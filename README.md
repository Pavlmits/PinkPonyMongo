# PinkPonyMongo

Pink pony mongo is a command line application that creates a collection in a mongo database that contains the git commits.

## Usage
To run the Pink pony application you have to download the [recently released version](https://github.com/Pavlmits/PinkPonyMongo/releases/latest).

##### Example

```bash
$ java -jar pinkponymongo.jar path\to\.git localhost db  27017 commits
```

* `localhost` : the host name
* `db` : database name
* `27017` : port number
* `commits` : collection name

##### Pink pony  
You can use also the [Pink Pony](https://github.com/Pavlmits/PinkPony) application that suggests functional clusters based on the common changes on git.