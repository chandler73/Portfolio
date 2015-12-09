-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 06, 2015 at 03:02 PM
-- Server version: 5.5.46-0ubuntu0.14.04.2
-- PHP Version: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dans_blog`
--

-- --------------------------------------------------------

--
-- Table structure for table `announcement`
--

CREATE TABLE IF NOT EXISTS `announcement` (
  `announcement_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `publish_date` date NOT NULL,
  `expiry_date` date NOT NULL,
  PRIMARY KEY (`announcement_id`),
  UNIQUE KEY `announcement_id` (`announcement_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `announcement`
--

INSERT INTO `announcement` (`announcement_id`, `content`, `publish_date`, `expiry_date`) VALUES
(1, 'Merry Christmas!', '2015-11-06', '2015-12-22');

-- --------------------------------------------------------

--
-- Table structure for table `blog_category`
--

CREATE TABLE IF NOT EXISTS `blog_category` (
  `blog_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  KEY `blog_id` (`blog_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `blog_category`
--

INSERT INTO `blog_category` (`blog_id`, `user_id`) VALUES
(1, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `blog_post`
--

CREATE TABLE IF NOT EXISTS `blog_post` (
  `blog_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  `submitted` tinyint(1) NOT NULL,
  `creation_date` date NOT NULL,
  `publish_date` date DEFAULT NULL,
  `expiry_date` date DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `blog_post`
--

INSERT INTO `blog_post` (`blog_id`, `user_id`, `title`, `submitted`, `creation_date`, `publish_date`, `expiry_date`, `content`) VALUES
(1, 1, 'Test Blog Post', 0, '2015-11-06', '2015-11-06', '2015-11-30', 'Skateboard asymmetrical migas, mumblecore lumbersexual health goth semiotics. YOLO cold-pressed sriracha kinfolk artisan, ramps pop-up mlkshk quinoa ugh messenger bag venmo synth chillwave umami. Keffiyeh food truck bespoke schlitz poutine chartreuse, beard tilde small batch pickled hella DIY. Trust fund fingerstache slow-carb ethical. Chartreuse heirloom meggings, poutine cliche echo park keytar salvia next level vegan. Umami tofu retro hella kogi, portland listicle meggings. YOLO plaid street art, master cleanse craft beer cardigan viral quinoa pabst.\r\n\r\nMeh you probably haven''t heard of them lumbersexual tousled, YOLO echo park listicle celiac kale chips. Chartreuse cold-pressed mlkshk synth, tote bag viral sustainable yuccie fixie plaid cliche pickled kickstarter vice green juice. Ennui venmo quinoa fap raw denim disrupt. Tilde squid twee etsy. Next level etsy locavore neutra you probably haven''t heard of them tofu raw denim chartreuse, photo booth marfa shabby chic fixie tilde. Narwhal pickled XOXO, gluten-free 3 wolf moon pabst fashion axe twee brooklyn banh mi you probably haven''t heard of them portland. Intelligentsia keffiyeh gastropub, try-hard actually polaroid shoreditch pitchfork tote bag listicle typewriter letterpress retro.\r\n\r\nChillwave you probably haven''t heard of them crucifix typewriter kitsch church-key bitters chambray, green juice gentrify. Thundercats celiac etsy knausgaard hammock letterpress. Kitsch hella quinoa YOLO, celiac cornhole slow-carb plaid etsy blue bottle ethical craft beer DIY tote bag. Vice readymade art party four dollar toast thundercats. 8-bit selvage lomo, green juice occupy scenester trust fund normcore shoreditch. Tote bag you probably haven''t heard of them cliche pork belly pickled craft beer, meh thundercats shabby chic cornhole migas. Wolf biodiesel literally typewriter, put a bird on it tattooed raw denim fingerstache 8-bit chartreuse waistcoat.'),
(2, 2, 'This Post Likely Sucks', 1, '2015-11-06', NULL, NULL, 'Lucas ipsum dolor sit amet skywalker calrissian antilles leia biggs ackbar calamari obi-wan organa jade. Lando darth solo coruscant kenobi darth jade ben. Maul windu jawa lobot skywalker han utapau yoda. Droid skywalker ackbar jango skywalker. Obi-wan wicket k-3po antilles hutt organa moff padmé watto. Padmé owen windu mustafar kashyyyk coruscant obi-wan skywalker mustafar. Moff grievous solo organa lando skywalker. Kessel kessel thrawn boba mace. Darth antilles ventress mandalorians padmé hutt.\r\n\r\nJinn dooku dooku solo jade jango moff. Lars binks padmé skywalker r2-d2 hutt moff. Solo antilles biggs organa chewbacca tatooine chewbacca jinn kashyyyk. Ben anakin wedge jango leia moff secura baba. Darth coruscant organa skywalker moff jawa lobot mustafar naboo. Wicket hutt dagobah k-3po darth. Organa antilles kessel luke. Ventress lando mandalorians biggs darth organa wedge. Antilles anakin c-3po luuke. Solo gonk wicket coruscant mon alderaan skywalker. Darth kessel amidala hutt kenobi skywalker organa kamino.\r\n\r\nBespin gamorrean amidala qui-gonn mara. Boba solo tatooine dagobah. Darth calamari secura lars obi-wan moff anakin hutt. Anakin greedo mandalorians bespin qui-gonn kamino antilles windu fett. Bothan antilles coruscant moff. Owen jabba sebulba padmé mon ahsoka boba antilles. Moff calrissian anakin solo. Jawa kenobi mon grievous hutt darth kenobi darth. Skywalker mandalore darth yavin mara calamari. Jade antilles binks leia anakin hutt antilles moff ackbar. Ben alderaan coruscant jawa baba. Yoda mon ackbar cade moff vader secura skywalker.\r\n\r\nSolo lando wedge droid jawa. Organa vader anakin hutt skywalker. Qui-gon yavin darth utapau coruscant. Darth ben binks aayla. Leia kashyyyk sidious maul moff hoth kenobi dagobah. Leia leia ackbar moff darth leia. Lando vader dantooine bespin windu. Darth antilles grievous aayla r2-d2 baba calrissian luke. Darth boba ben fett moff coruscant mon. Darth skywalker boba obi-wan mon darth. Twi''lek antilles dantooine mara ben baba anakin. Dantooine kessel kessel darth mon antilles dantooine. Ponda jinn dantooine wampa moff jinn han.');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`category_id`, `category`) VALUES
(1, 'really mad'),
(2, 'really sad');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `authority` varchar(20) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `user_name`, `password`, `authority`) VALUES
(1, 'admin', 'dannyboy', 'role_admin'),
(2, 'blogger', 'ghostie', 'role_blogger');

-- --------------------------------------------------------

--
-- Table structure for table `web_pages`
--

CREATE TABLE IF NOT EXISTS `web_pages` (
  `page_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`page_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `web_pages`
--

INSERT INTO `web_pages` (`page_id`, `title`, `content`) VALUES
(1, 'Don''t Be a Wench', 'Shiver me timbers American Main Yellow Jack pillage Nelsons folly Davy Jones'' Locker scourge of the seven seas quarterdeck draught jury mast. Splice the main brace yawl hornswaggle flogging ho quarter doubloon mutiny coffer killick. Corsair Privateer handsomely nipperkin holystone belay provost black jack gaff schooner.\r\nClap of thunder sutler spike Plate Fleet lugger bilge water furl Buccaneer poop deck cutlass. Lugsail weigh anchor tackle take a caulk broadside bowsprit belay tender killick Blimey. Heave to hogshead scuppers pillage tender Sink me blow the man down to go on account long clothes gally.\r\nYardarm crack Jennys tea cup hornswaggle gangplank hulk ahoy draught Shiver me timbers pink gangway. Spanker strike colors draught mizzen fluke rum rigging cable Corsair pink. Spike bucko skysail sutler spyglass haul wind walk the plank hulk loaded to the gunwalls Sea Legs.');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
