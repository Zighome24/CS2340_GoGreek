const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

//Hello World function
exports.helloWorld = functions.https.onRequest((request, response) => {
    response.send("Hello from Firebase!");
});

exports.getUniqueKey = functions.https.onRequest((request, response) => {
    const dbRef = admin.database().ref("/nextUniqueKey");
    dbRef.once("value").then(snapshot => {
        var milliseconds = (new Date).getTime();
        console.log("Snapshot: " + (snapshot));
        console.log("Time in milliseconds: " + milliseconds);
        console.log("Next Unique Key: " + snapshot.val);
        var retObj = {};
        retObj["key"] = snapshot.val();
        retObj["time"] = milliseconds;
        response.send(JSON.stringify(retObj));
        dbRef.set(snapshot.val() + 1).then(function() {
            console.log("The server value has been incremented.");
        }).catch(function(error) {
            console.log("The server value has not been incremented. Failed with error: " + error);
        })
    }, reason => {
        console.log("The Promise failed for reason: " + reason);
        response.status(500).send('The Request failed for reason: ' + reason);
    });
});
