(function() {

    var db = {

        loadData: function(filter) {
            return $.grep(this.clients, function(client) {
                return (!filter.Writer || client.Writer.indexOf(filter.Writer) > -1)
                    && (filter.Id === undefined || client.Id === filter.Id)
                    && (!filter.Title || client.Title.indexOf(filter.Title) > -1)
                    && (!filter.CreatedDate || client.CreatedDate === filter.CreatedDate);
            });
        },

        insertItem: function(insertingClient) {
            this.clients.push(insertingClient);
        },

        updateItem: function(updatingClient) { },

        deleteItem: function(deletingClient) {
            var clientIndex = $.inArray(deletingClient, this.clients);
            this.clients.splice(clientIndex, 1);
        }

    };

    window.db = db;

    db.clients = [
        {
            "Writer": "Otto Clay",
            "Id": 1,
            "Title": "Ap #897-1459 Quam Avenue",
            "CreatedDate": "2022-08-20 24:00:00"
        },
        {
            "Writer": "Lysandra Pierce",
            "Id": 36,
            "Title": "Ap #146-2835 Curabitur St.",
            "CreatedDate": "2022-08-20 24:00:00"
        }
    ];



}());