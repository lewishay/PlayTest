package helpers

import javax.inject.Inject

import play.http.DefaultHttpFilters

class Filters @Inject() (log: LoggingFilter, hang: HangmanFilter) extends DefaultHttpFilters(log, hang)
