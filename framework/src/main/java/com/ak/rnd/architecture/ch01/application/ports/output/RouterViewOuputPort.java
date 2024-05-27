package com.ak.rnd.architecture.ch01.application.ports.output;

import com.ak.rnd.architecture.ch01.domain.Router;

import java.util.List;

/**
 * There are situations in which a use case needs to fetch data from external resources to achieve its goals. That's the
 * role of output ports, which are represented as interfaces describing, in a technology-agnostic way, which kind of data
 * a use case or input port would need to get from outside to perform its operations. I say agnostic because output
 * ports don't care if the data comes from a particular relational database technology or a filesystem, for example.
 * We assign this responsibility to output adapters
 */
public interface RouterViewOuputPort {

    List<Router> fetchRouters();
}
