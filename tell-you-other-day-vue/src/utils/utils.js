export const isNotNullORBlank = (...args)=> {
    for (var i = 0; i < args.length; i++) {
      var argument = args[i];
      if (argument == null || argument == '' || argument == undefined || argument == 'null') {
        return false;
      }
    }
    return true;
  }
  