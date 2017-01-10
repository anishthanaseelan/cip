# The `src/common` Directory

This directory houses all internal and 3rd party reusable components.

Each type of components resides in its own directory. 

```
src/
|- common/
|	|- directives/
|	|- i18n/
|	|	|- locales/
|	|- services
```

- `directives` - houses reusable custom directives.
- `i18n` - houses the country specific resource files from which the messages, headers and field names must be inferred.
		   this helps in internationalizing strings.
- `services` - All the common services goes here. For e.g. Authorization Service, linksMapper, localeDetector, etc.,