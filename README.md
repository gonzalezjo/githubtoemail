# githubtoemail


Really simple and lightweight use of the GitHub API to find a GitHub user's email. 


It attempts to: 


1) Prints the public email associated with the account if there is one. 


2) Print the public email addresses based on the user's recent activity. 


3) Print the public email addresses based on the user's own repos (ignoring forks)


To compile to a jar, use `mvn install`. Precompiled jars are in [releases](https://github.com/gonzalezjo/githubtoemail/releases).


Oh, and I totally forgot that email regex was a thing and went with (sorry!) string splitting. Sorry about that.
