$(document)
        .ready(
        function() {
            var crudServiceBaseUrl = BaseUrl + "/admin", dataSource = new kendo.data.DataSource(
                    {
                        transport: {
                            read: {
                                url: crudServiceBaseUrl + "/r/"
                                        + Entidad + "/",
                                dataType: "json"
                            },
                            update: {
                                url: crudServiceBaseUrl + "/u/"
                                        + Entidad + "/",
                                dataType: "json"
                            },
                            destroy: {
                                url: crudServiceBaseUrl + "/d/"
                                        + Entidad + "/",
                                dataType: "json"
                            },
                            create: {
                                url: crudServiceBaseUrl + "/c/"
                                        + Entidad + "/",
                                dataType: "json"
                            },
                            parameterMap: function(options, operation) {
                                if (operation !== "read"
                                        && options.models) {
                                    return {
                                        models: kendo
                                                .stringify(options.models)
                                    };
                                }
                            }
                        },
                        batch: true,
                        error: function(e) {
                            console.log("Error Status: " + e.status);
                        },
                        requestStart: function(e) {
                            console.log("Request Start");
                        },
                        requestEnd: function(e) {
                            console.log(e.type);
                            if (e.type !== "read") {
                                $("#grid").data("kendoGrid").dataSource.read();
                            }
                        },
                        change: function(e) {
                            var data = this.data();
                            console.log(data.length);
                        },
                        pageSize: 10,
                        schema: {
                            model: {
                                id: ID,
                                fields: Fields
                            }
                        }
                    });

            $("#grid").kendoGrid({
                dataSource: dataSource,
                sortable: true,
                groupable: {
                    messages: {
                        empty: "Arrastre una columna para agrupar"
                    }
                },
                filterable:
                        {
                            messages: {
                                info: "Filtrar:", // sets the text on top of the filter menu
                                filter: "filtrar", // sets the text for the "Filter" button
                                clear: "limpiar", // sets the text for the "Clear" button

                                // when filtering boolean numbers
                                isTrue: "verdadero", // sets the text for "isTrue" radio button
                                isFalse: "falso", // sets the text for "isFalse" radio button

                                //changes the text of the "And" and "Or" of the filter menu
                                and: "Y",
                                or: "O"
                            },
                            operators: {
                                //filter menu for "string" type columns
                                string: {
                                    eq: "Igual a",
                                    neq: "Diferente de",
                                    startswith: "Comienza con",
                                    contains: "Contiene",
                                    endswith: "Termina con"
                                },
                                //filter menu for "number" type columns
                                number: {
                                    eq: "Igual",
                                    neq: "Diferent",
                                    gte: "Mayor",
                                    gt: "Mayor o igual",
                                    lte: "Menor o igual",
                                    lt: "Menor"
                                },
                                //filter menu for "date" type columns
                                date: {
                                    eq: "Custom Equal to",
                                    neq: "Custom Not equal to",
                                    gte: "Custom Is after or equal to",
                                    gt: "Custom Is after",
                                    lte: "Custom Is before or equal to",
                                    lt: "Custom Is before"
                                },
                                //filter menu for foreign key values
                                enums: {
                                    eq: "custom Is Equal to",
                                    neq: "custom Is Not equal to"
                                }
                            }
                        },
                pageable: {
                    messages: {
                        display: "{0} - {1} de {2} registros", //{0} is the index of the first record on the page, {1} - index of the last record on the page, {2} is the total amount of records
                        empty: "No existen registros",
                        page: "Pagina",
                        of: "de {0}", //{0} is total amount of pages
                        itemsPerPage: "registros por pagina",
                        first: "Primera pagina",
                        previous: "Pagina anterior",
                        next: "Pagina siguiente",
                        last: "Ultima Pagina",
                        refresh: "Refrescar"
                    }
                },
                height: 430,
                columns: Columns,
                edit: function(e) {
                    $("#grid").closest(".k-upload-button").find("span").text("subir...");
                },
                editable: "popup",
                toolbar: [{name: "create", text: "Añadir"}]
            });
        });