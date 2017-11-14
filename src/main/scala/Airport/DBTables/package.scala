package Airport

import java.sql.Timestamp
import java.time.Instant

import slick.ast.BaseTypedType
import slick.jdbc.PostgresProfile.api._

package object DBTables {
  val companies         = TableQuery[CompanyTable]
  val trips             = TableQuery[TripTable]
  val passengers        = TableQuery[PassengerTable]
  val passengersInTrips = TableQuery[PassengerInTripTable]

  implicit val instantToTimestamp: BaseTypedType[Instant] =
    MappedColumnType.base[Instant, Timestamp](
      instant => Timestamp.from(instant),
      timestamp => timestamp.toInstant
    )

  val createSchema: DBIO[Unit] =
    companies.schema.create >> trips.schema.create >> passengers.schema.create >> passengersInTrips.schema.create

  val truncateTables: DBIO[Unit] =
    passengersInTrips.schema.truncate >> passengers.schema.truncate >> trips.schema.truncate >> companies.schema.truncate

  val dropSchema: DBIO[Unit] =
    passengersInTrips.schema.drop >> passengers.schema.drop >> trips.schema.drop >> companies.schema.drop
}
