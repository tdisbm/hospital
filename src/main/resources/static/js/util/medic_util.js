define(["lodash"], (_) => {
    return {
        responseHandlers: {},

        setResponseHandler(status, f) {
            if (_.isInteger(status) && _.isFunction(f)) {
                this.responseHandlers[status] = f;
            }
            return this;
        },

        checkAvailability (input) {
            const medicId = _.get(input, "medicId");
            const requestBody = {
                date: _.get(input, "date", null)
            };
            if (date === "" || _.isUndefined(medicId)) {
                return;
            }

            $.post("/medic/availability/check/" + medicId, requestBody).always(
                (body, status_text, response) => {
                    const handler = _.get(this.responseHandlers, response.status, null);
                    if (handler !== null && _.isFunction(handler)) {
                        handler(response);
                    }
                })
        }
    }
});